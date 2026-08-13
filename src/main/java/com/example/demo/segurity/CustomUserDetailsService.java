package com.example.demo.segurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.config.SecurityConstants;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if (!SecurityConstants.USERNAME.equals(username)) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
                .username(SecurityConstants.USERNAME)
                .password(SecurityConstants.PASSWORD)
                .roles(SecurityConstants.ROLE)
                .build();
    }
}