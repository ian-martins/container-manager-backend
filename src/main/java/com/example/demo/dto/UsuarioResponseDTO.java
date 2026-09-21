package com.example.demo.dto;

import java.util.UUID;

public record UsuarioResponseDTO(UUID id, String username, UUID dockerHostId, String hostName, String role) {
    
}
