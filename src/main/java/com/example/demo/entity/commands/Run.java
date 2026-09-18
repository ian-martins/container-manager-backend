package com.example.demo.entity.commands;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Run {
    
    private boolean detached;
    private boolean remove;
    private boolean save;
    private boolean interactive;
    private boolean tty;

    private String image;
    private String memory;
    private String cpus;
    
    private String timeout;
    private String signal;
    private String name;
    
    private List<String> environments = new ArrayList<>();
    private List<String> ports = new ArrayList<>();
    private List<String> volumes = new ArrayList<>();
    private List<String> mounts = new ArrayList<>();


}