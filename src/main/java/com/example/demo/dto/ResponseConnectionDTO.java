package com.example.demo.dto;

import java.util.UUID;

public record ResponseConnectionDTO(UUID id, String desc, String host, int  port, boolean wsl, boolean active) {
}
