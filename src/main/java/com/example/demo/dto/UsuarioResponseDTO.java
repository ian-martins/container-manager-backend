package com.example.demo.dto;

import com.example.demo.entity.Role;

public record UsuarioResponseDTO(String id, String dockerHostId, Role role, String username) {
    
}
