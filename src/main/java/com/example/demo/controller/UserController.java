package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ActiveConnectionRequestDTO;
import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.segurity.CustomUserDetails;
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

}
/*[
  {
    "id": "7da9affb-f236-40ea-9f62-c3841dd7b4d1",
    "desc": "WSL",
    "host": "localhost",
    "port": 5000,
    "wsl": true,
    "active": false
  },
  {
    "id": "ee177afd-9081-423d-bff6-f36a82f4b774",
    "desc": "Host Oracle",
    "host": "10.211.0.31",
    "port": 2375,
    "wsl": false,
    "active": false
  },
  {
    "id": "b1170e47-7d28-4728-bc74-8ded51c56577",
    "desc": "6",
    "host": "6",
    "port": 6,
    "wsl": false,
    "active": true
  },
  {
    "id": "b1170e47-7d28-4728-bc74-8ded51c56577",
    "desc": "6",
    "host": "6",
    "port": 6,
    "wsl": false,
    "active": false
  },
  {
    "id": "b7c391a3-5623-4dab-9652-13d8a59badec",
    "desc": "e",
    "host": "e",
    "port": 2,
    "wsl": false,
    "active": false
  }
] */