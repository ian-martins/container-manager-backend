package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DockerHost;

public interface DockerHostRepository extends JpaRepository<DockerHost, UUID> {
    Optional<DockerHost> findByName(String name);
}