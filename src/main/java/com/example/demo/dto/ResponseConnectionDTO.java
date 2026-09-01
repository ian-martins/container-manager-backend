package com.example.demo.dto;

import java.util.UUID;

public record ResponseConnectionDTO(UUID id, String name, String host, String port, boolean wsl, boolean active) {
}
