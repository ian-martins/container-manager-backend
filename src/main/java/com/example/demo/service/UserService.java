package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConnectionService connectionService;

    public UserService(UserRepository userRepository, ConnectionService connectionService){
        this.userRepository = userRepository;
        this.connectionService = connectionService;
    }
    
    public void salvarUsuario(Usuario usuario){
        userRepository.save(usuario);
    }
    public void activate(UUID id, Usuario usuario){
        connectionService.activate(id, usuario);
    }

    public Usuario findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<Usuario> findAll() {
        return userRepository.findAll();
    }
    
}
