package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.config.ActiveConnection;
import com.example.demo.config.DockerHost;
import com.example.demo.model.Usuario;
import com.example.demo.repository.DockerHostRepository;

@Service
public class ConnectionService {

    private final DockerHostRepository repository;
    private final ActiveConnection activeConnection;

    public ConnectionService(DockerHostRepository repository, ActiveConnection activeConnection) {
        this.repository = repository;
        this.activeConnection = activeConnection;
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

    public DockerHost update(DockerHost connection) {
        return repository.update(connection);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void activate(UUID id, Usuario usuario) {
        DockerHost connection = findById(id).get();
        
        if (connection == null) {
            throw new RuntimeException("Conexão não encontrada");
        }
        usuario.setDockerHost(connection);
    }
    
    public DockerHost getActiveConnection() {
        return activeConnection.getConnection();
    }
}
