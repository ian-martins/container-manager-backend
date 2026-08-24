package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UsuarioUpdateRequest;
import com.example.demo.entity.Usuario;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UsuarioUpdateRequest usuarioUpdateRequest, Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        userService.update(usuario, usuarioUpdateRequest);
        
        return ResponseEntity.ok().build();
    }
}
