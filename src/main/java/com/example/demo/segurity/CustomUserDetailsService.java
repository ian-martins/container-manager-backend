package com.example.demo.segurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.service.UserService;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    
    public CustomUserDetailsService(UserService  userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userService.findByUsername(username);
    }
}