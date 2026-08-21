package com.example.demo.config;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DockerHost {

    private UUID id;
    private String name;
    private String host;
    private int port;
    private boolean wslLocal;

}