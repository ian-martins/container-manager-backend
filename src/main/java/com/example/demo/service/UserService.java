package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ConnectionService;


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
    
}
