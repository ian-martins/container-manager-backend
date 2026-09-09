package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Historico;

public interface HistoricoReposytory extends JpaRepository<Historico, UUID>{
    
}
