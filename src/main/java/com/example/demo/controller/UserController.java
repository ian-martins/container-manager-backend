package com.example.demo.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.service.ConnectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class UserController {

    private final UserController userController;
    private final ConnectionService connectionService;
    
    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        connectionService.activate(id, usuario);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> s(@PathVariable UUID id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        connectionService.activate(id, usuario);
        return ResponseEntity.ok().build();
    }


}
