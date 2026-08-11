package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Command_Run;
import com.example.demo.model.Object_Container;
import com.example.demo.model.RemoveResponse;
import com.example.demo.service.CommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class HomeController {
    private final CommandService commandService;

    @GetMapping("/container")
    public ResponseEntity<?> containers() {
        Optional<List<Object_Container>> containers = commandService.containers(true);
        if (containers.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(containers);
        else
            return ResponseEntity.ok().body(containers);
    }
    
    @GetMapping("/container/{id}")
    public ResponseEntity<?> container(@PathVariable("id") String id) {
        Optional<Object_Container> container = commandService.container(id, id);
        if (container.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Container Não Encontrado");
        else
            return ResponseEntity.ok().body(container);
    }

    @PostMapping("/container/run")
    public ResponseEntity<?> run(@RequestBody Command_Run command_Run) {
        commandService.run(command_Run);
        return ResponseEntity.ok().body("criado");
    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<?> stop(@PathVariable("id") String id) {
        if (commandService.stop(id)) ResponseEntity.ok().body(commandService.container(id, id).get());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao parar Container " + id);
    }
    
    @GetMapping("/start/{id}")
    public ResponseEntity<?> start(@PathVariable("id") String id) {
        if(!commandService.container(id, "").isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Container " +  id + " Não Encontrado");
        if(commandService.start(id)) return ResponseEntity.status(HttpStatus.ACCEPTED).body("Container " + id + " Reiniciado");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Falha ao parar Container " + id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") String id){
        if(commandService.container(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RemoveResponse(id, false, "Impossivel Deletar Container " + id + ", esta Rodando!")); 
        }
        if(commandService.remove(id)) return ResponseEntity.status(HttpStatus.OK).body(new RemoveResponse(id, true, "Container " + id + " Deletado"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}



