package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.config.DockerHost;

public interface DockerHostRepository {

    List<DockerHost> findAll();
    Optional<DockerHost> findById(UUID id);
    DockerHost save(DockerHost connection);
    DockerHost update(DockerHost connection);
    void deleteById(UUID id);
    void deleteAll();
}