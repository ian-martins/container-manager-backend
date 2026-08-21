package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.DockerHost;
import com.example.demo.model.Object_Container;
import com.example.demo.model.Usuario;
import com.example.demo.model.commands.Command_Run;
import com.example.demo.model.dto.GenericResponse;
import com.example.demo.service.CommandService;
import com.example.demo.service.ConnectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class HomeController {

    private final CommandService commandService;
    private final ConnectionService connectionService;

    @GetMapping("/container")
    public ResponseEntity<?> containers(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DockerHost dockerHost = connectionService.findById(usuario.getDockerHost()).get();

        Optional<List<Object_Container>> containers = commandService.containers(true, dockerHost);
        if (containers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(containers);
        } else {
            return ResponseEntity.ok().body(containers);
        }
    }

    @GetMapping("/container/{id}")
    public ResponseEntity<?> container(@PathVariable("id") String id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DockerHost dockerHost = connectionService.findById(usuario.getDockerHost()).get();

        Optional<Object_Container> container = commandService.container(id, id, dockerHost);
        if (container.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Container Não Encontrado");
        } else {
            return ResponseEntity.ok().body(container);
        }
    }

    @PostMapping("/container/run")
    public ResponseEntity<?> run(@RequestBody Command_Run command_Run, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DockerHost dockerHost = connectionService.findById(usuario.getDockerHost()).get();

        commandService.run(command_Run, dockerHost);
        return ResponseEntity.ok().body("criado");
    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<?> stop(@PathVariable("id") String id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DockerHost dockerHost = connectionService.findById(usuario.getDockerHost()).get();

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
    public ResponseEntity<?> start(@PathVariable("id") String id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DockerHost dockerHost = connectionService.findById(usuario.getDockerHost()).get();

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
    public ResponseEntity<?> remove(@PathVariable("id") String id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DockerHost dockerHost = connectionService.findById(usuario.getDockerHost()).get();

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
