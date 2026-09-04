package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.segurity.SecurityConfig;

@Service
public class UserService {

    private final UsuarioRepository userRepository;
    private final HostRepository hostRepository;

    private final SecurityConfig securityConfig;

    public UserService(HostRepository hostRepository, UsuarioRepository userRepository,
            ConnectionService connectionService,
            SecurityConfig securityConfig) {
        this.hostRepository = hostRepository;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public Usuario salvarUsuario(Usuario usuario, UsuarioRequestDTO usuarioDTO) {
   
        if (usuarioDTO.username() != null && !usuarioDTO.username().isBlank()) {
            usuario.setUsername(usuarioDTO.username());
        }
        if (usuarioDTO.password() != null && !usuarioDTO.password().isBlank()) {
            usuario.setPassword(securityConfig.passwordEncoder().encode(usuarioDTO.password()));
        }
        return userRepository.save(usuario);
    }
    
    public Usuario salvarUsuario(Usuario usuario) {
        return userRepository.save(usuario);
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

    public Usuario activeConn(Usuario usuario, UUID id){
        usuario.setDockerHostId(id);
        return salvarUsuario(usuario);
    }    
}
