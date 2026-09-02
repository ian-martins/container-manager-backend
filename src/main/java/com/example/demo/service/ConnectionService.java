package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Host;
import com.example.demo.repository.HostRepository;

@Service
public class ConnectionService {

    private final HostRepository repository;

    public ConnectionService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findAll() {
        return repository.findAll();
    }

    public Optional<Host> findById(UUID id) {
        return repository.findById(id);
    }

    public Host save(Host connection) {
        return repository.save(connection);
    }

    public Host update(Host connection) {
        return repository.save(connection);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
