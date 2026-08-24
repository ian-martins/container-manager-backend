package com.example.demo.dto;

public record UsuarioUpdateRequest(String id, String username, String dockerHostId, String password, String role) {
    
}
/*
[
    {
        "id": "77496aab-7629-439d-a798-84f8b7fe7164",
        "username": "ian",
        "password": "$2a$10$H8nm061mlLeBJsQtrAkWdeOgG6.BxysHWUJ3gQAPI4LSQs7yAoT5.",
        "dockerHostId": "77496aab-7629-439d-a798-84f8b7fe7165",
        "role": "ADMIN"
    }
]
     */