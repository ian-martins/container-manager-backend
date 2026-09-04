package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RequestDockerHostDTO;
import com.example.demo.dto.ResponseConnectionDTO;
import com.example.demo.entity.Host;
import com.example.demo.entity.Usuario;
import com.example.demo.segurity.CustomUserDetails;
import com.example.demo.service.ConnectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/connection")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class ConnectionController {

    public final ConnectionService connectionService;
    
    @PostMapping("/new")
    @PreAuthorize("hasAuthority('CONNECTION_CREATE')")
    public ResponseEntity<?> newConnection(@RequestBody RequestDockerHostDTO dto) {
        try {
            Host saved = connectionService.save(new Host(null, dto.name(), dto.host(), dto.port(), dto.wsl()));
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar conexão: " + e);
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('CONNECTION_READ')")
    public ResponseEntity<?> listConnections(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();

        try {
            List<Host> listD = connectionService.findAll();
            Host dh = new Host();
            List<ResponseConnectionDTO> response = new ArrayList<>();

            for (int i = 0; i < listD.size(); i++) {
                dh = listD.get(i);
                if (usuario.getDockerHostId().equals(dh.getId())) {
                    response.add(dh.response(true));
                }else{
                    response.add(dh.response(false));
                }
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao Listar as conexões: " + e);
        }
    }

    @GetMapping("/list/{id}")
    @PreAuthorize("hasAuthority('CONNECTION_READ')")
    public ResponseEntity<?> listConnections(@PathVariable("id") UUID id) {
        try {
            Optional<Host> optional = connectionService.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("conexão não encontrada");
            }
            return ResponseEntity.ok(optional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encontrar conexão: " + e);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('CONNECTION_DELETE')")
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
    @PreAuthorize("hasAuthority('CONNECTION_DELETE')")
    public ResponseEntity<?> deleteConnections() {
        try {
            connectionService.deleteAll();
            return ResponseEntity.ok().body("Conexões deletadas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar conexões: " + e);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('CONNECTION_UPDATE')")
    public ResponseEntity<?> updateConnections(@RequestBody Host dockerHost) {
        Optional<Host> dockerHostOld = connectionService.findById(dockerHost.getId());

        if (dockerHostOld.isPresent()) {
            return ResponseEntity.ok(connectionService.save(dockerHost));
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Conexão não atualizada");
    }

}
