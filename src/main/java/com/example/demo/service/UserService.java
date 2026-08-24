package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.DockerHostRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.segurity.SecurityConfig;

@Service
public class UserService {

    private final UsuarioRepository userRepository;
    private final DockerHostRepository dockerHostRepository;

    private final SecurityConfig securityConfig;

    public UserService(DockerHostRepository dockerHostRepository, UsuarioRepository userRepository,
            ConnectionService connectionService,
            SecurityConfig securityConfig) {
        this.dockerHostRepository = dockerHostRepository;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public void salvarUsuario(Usuario usuario, UsuarioRequestDTO usuarioDTO) {
        if (usuarioDTO.dockerHostId() != null && !usuarioDTO.dockerHostId().isBlank()) {
            if (!dockerHostRepository.existsById(UUID.fromString(usuarioDTO.dockerHostId()))) {
                throw new RuntimeException("Docker Host não encontrado");
            }
            usuario.setDockerHostId(UUID.fromString(usuarioDTO.dockerHostId()));
        }
        if (usuarioDTO.username() != null && !usuarioDTO.username().isBlank()) {
            usuario.setUsername(usuarioDTO.username());
        }
        if (usuarioDTO.password() != null && !usuarioDTO.password().isBlank()) {
            usuario.setPassword(securityConfig.passwordEncoder().encode(usuarioDTO.password()));
        }
        userRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> findAll() {
        // como transformo isso:
        List<Usuario> usuarios = userRepository.findAll();
        // nisso:
        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId().toString(),
                        usuario.getDockerHostId() != null ? usuario.getDockerHostId().toString() : null,
                        usuario.getRole(),
                        usuario.getUsername()))
                .toList();
        return responseDTOs;
    }

}
