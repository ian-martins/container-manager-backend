package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GenericResponse;
import com.example.demo.entity.Container;
import com.example.demo.entity.Host;
import com.example.demo.entity.Usuario;
import com.example.demo.entity.commands.Command_Run;
import com.example.demo.segurity.CustomUserDetails;
import com.example.demo.service.ConnectionService;
import com.example.demo.service.DockerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class DockerController {

    private final DockerService commandService;
    private final ConnectionService connectionService;

    @GetMapping("/container")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'VIEWER')")
    public ResponseEntity<?> containers(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        Host dockerHost = connectionService.findById(usuario.getDockerHostId()).get();
        
        Optional<List<Container>> containers = commandService.containers(true, dockerHost);
        if (containers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(containers);
        } else {
            return ResponseEntity.ok().body(containers);
        }
    }

    @GetMapping("/container/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'VIEWER')")
    public ResponseEntity<?> container(@PathVariable("id") String id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        Host dockerHost = connectionService.findById(usuario.getDockerHostId()).get();

        Optional<Container> container = commandService.container(id, id, dockerHost);
        if (container.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Container Não Encontrado");
        } else {
            return ResponseEntity.ok().body(container);
        }
    }

    @PostMapping("/container/run")
    public ResponseEntity<?> run(@RequestBody Command_Run command_Run, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        Host dockerHost = connectionService.findById(usuario.getDockerHostId()).get();

        commandService.run(command_Run, dockerHost);
        return ResponseEntity.ok().body("criado");
    }

    @GetMapping("/stop/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> stop(@PathVariable("id") String id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        Host dockerHost = connectionService.findById(usuario.getDockerHostId()).get();

        try {
            if (commandService.container(id, "", dockerHost).isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new GenericResponse(id, false, null, "Container " + id + " Não Encontrado"));
            }
            commandService.stop(id, dockerHost);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(id, true, null, "Container " + id + " Parado"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse(null, false, e.getMessage(), "Falha interna do Servidor"));

        }
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/start/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> start(@PathVariable("id") String id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        Host dockerHost = connectionService.findById(usuario.getDockerHostId()).get();

        try {
            if (!commandService.container(id, "", dockerHost).isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new GenericResponse(id, false, null, "Container " + id + " Não Encontrado"));
            }
            commandService.start(id, dockerHost);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(id, true, null, "Container " + id + " Reiniciado"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse(null, false, e.getMessage(), "Falha interna do Servidor"));
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> remove(@PathVariable("id") String id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        Host dockerHost = connectionService.findById(usuario.getDockerHostId()).get();

        try {
            if (commandService.container(id, dockerHost)) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new GenericResponse(id, false, null, "Impossivel Deletar Container " + id + ", esta Rodando!"));
            }
            commandService.remove(id, dockerHost);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(id, true, null, "Container " + id + " Deletado"));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse(null, false, e.getMessage(), "Falha interna do Servidor"));
        }
    }
}
