package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:5173/")
public class TesteController {

    @GetMapping("/teste")
    @PreAuthorize("hasAuthority('CONTAINER_DELETE')")
    public ResponseEntity<?> teste(Authentication authentication) {
        System.out.println("===== ENTROU NO CONTROLLER =====");
        System.out.println("USUARIO: " + authentication.getPrincipal());
        System.out.println("AUTHORITIES: " + authentication.getAuthorities());

        return ResponseEntity.ok("OK");
    }
}
