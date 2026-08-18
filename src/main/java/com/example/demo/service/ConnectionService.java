package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.config.DockerHost;
import com.example.demo.repository.DockerHostRepository;

@Service
public class ConnectionService {

    private final DockerHostRepository repository;

    public ConnectionService(DockerHostRepository repository) {
        this.repository = repository;
    }

    public List<DockerHost> findAll() {
        return repository.findAll();
    }

    public Optional<DockerHost> findById(UUID id) {
        return repository.findById(id);
    }

    public DockerHost save(DockerHost connection) {
        if (connection.getId() == null) {
            connection.setId(UUID.randomUUID());
        }

        return repository.save(connection);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}