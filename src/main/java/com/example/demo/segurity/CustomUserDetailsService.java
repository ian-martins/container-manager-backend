package com.example.demo.segurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.config.UserConstants;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if (!UserConstants.USERNAME.equals(username)) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
                .username(UserConstants.USERNAME)
                .password(UserConstants.PASSWORD)
                .roles(UserConstants.ROLE)
                .build();
    }
}