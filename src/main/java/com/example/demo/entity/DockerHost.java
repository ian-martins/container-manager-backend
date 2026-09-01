package com.example.demo.entity;

import java.util.UUID;

import com.example.demo.dto.ResponseConnectionDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "docker_hosts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DockerHost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String host;
    private Integer port;
    private boolean wslLocal;

    public ResponseConnectionDTO response(boolean active){
        return new ResponseConnectionDTO(
            id,
            name != null ? name : "",
            host != null ? host : "", 
            port != 0    ? port.toString() : "", 
            wslLocal, 
            active
        );
    }
}