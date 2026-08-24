package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DockerHost;
import com.example.demo.service.ConnectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/connection")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class ConnectionController {

    public final ConnectionService connectionService;

    @PostMapping("/new")
    public ResponseEntity<?> newConnection(@RequestBody DockerHost dockerHost) {
        try {
            DockerHost saved = connectionService.save(dockerHost);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar conexão: " + e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listConnections() {
        try {
            List<DockerHost> listD = connectionService.findAll();
            return ResponseEntity.ok(listD);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao Listar as conexões: " + e);
        }
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> listConnections(@PathVariable("id") UUID id) {
        try {
            Optional<DockerHost> optional = connectionService.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("conexão não encontrada");
            }
            return ResponseEntity.ok(optional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encontrar conexão: " + e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteConnections(@PathVariable("id") UUID id) {
        try {
            if (connectionService.findById(id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conexão não existe");
            }
            connectionService.delete(id);
            if (connectionService.findById(id).isEmpty()) {
                return ResponseEntity.ok().body("Conexão deletada");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("conexão não foi deletada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar conexão: " + e);
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteConnections() {
        try {
            connectionService.deleteAll();
            return ResponseEntity.ok().body("Conexões deletadas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar conexões: " + e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateConnections(@RequestBody DockerHost dockerHost) {
        Optional<DockerHost> dockerHostOld = connectionService.findById(dockerHost.getId());
        
        if (dockerHostOld.isPresent()) {
             return ResponseEntity.ok(connectionService.save(dockerHost));
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Conexão não atualizada");
    }

}
