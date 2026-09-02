package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Host;

public interface HostRepository extends JpaRepository<Host, UUID> {
    Optional<Host> findByName(String name);
}