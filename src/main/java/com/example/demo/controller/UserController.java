package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ActiveConnectionRequestDTO;
import com.example.demo.dto.CreateUsuarioDTO;
import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.segurity.CustomUserDetails;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://10.211.25.199:3000"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUset(@RequestBody UsuarioRequestDTO usuarioDTO, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        userService.salvarUsuario(usuario, usuarioDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/activeconn")
    public ResponseEntity<?> activeConnection(@RequestBody ActiveConnectionRequestDTO dto,
            Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        
        return ResponseEntity.ok(userService.activeConn(usuario, dto.id()));
    }

    @PostMapping ("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUsuarioDTO dto){
        return ResponseEntity.ok().build();
    }
}