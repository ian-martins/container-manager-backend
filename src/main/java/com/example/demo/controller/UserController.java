package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ConnectionService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class UserController {

    private final ConnectionService connectionService;
    private final UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    //@PutMapping("/")
}
