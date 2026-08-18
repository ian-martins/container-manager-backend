package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.config.DockerHost;

public interface DockerHostRepository {

    List<DockerHost> findAll();
    Optional<DockerHost> findById(int id);
    DockerHost save(DockerHost connection);
    void deleteById(int id);
}