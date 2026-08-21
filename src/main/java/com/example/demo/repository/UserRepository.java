package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.model.Usuario;

public interface  UserRepository{
    
    List<Usuario> findAll();
    Optional<Usuario> findById(UUID id);
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    void deleteById(UUID id);
    void deleteAll();
}