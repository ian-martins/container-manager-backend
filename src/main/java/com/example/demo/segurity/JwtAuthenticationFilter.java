package com.example.demo.segurity;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        System.out.println("===== JWT FILTER =====");
        String authHeader = request.getHeader("Authorization");
        System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("SEM TOKEN");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("TOKEN ENCONTRADO");
        String jwt = authHeader.substring(7);

        String username;
        try {
            username = jwtService.extractUsername(jwt);
            System.out.println("USERNAME EXTRAÍDO: " + username);
        } catch (Exception e) {
            System.out.println("ERRO AO EXTRAIR USERNAME: " + e.getMessage());

            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("CARREGANDO USUARIO...");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("USUARIO CARREGADO: " + userDetails.getUsername());
            System.out.println("AUTHORITIES: " + userDetails.getAuthorities().toString());

            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("TOKEN VALIDO");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
                System.out.println(
                        "SECURITY CONTEXT: " +
                                SecurityContextHolder.getContext().getAuthentication());
            } else {
                System.out.println("TOKEN INVALIDO");
            }
        }
        System.out.println("PASSANDO PARA PROXIMO FILTRO");
        filterChain.doFilter(request, response);
    }
}