package com.example.demo.model.commands;

import java.util.List;

public class Command_Run extends Command_Generic{
    
    private boolean detached;
    private boolean remove;

    private String image;
    private String memory;
    private String cpus;

    public Command_Run() {
    }

    public Command_Run(
        boolean detached, 
        boolean remove, 
        boolean interactive,
        boolean tty, 
        String image, 
        String memory,
        String cpus,
        String timeout,
        String signal, 
        String name,
        List<String> environments,
        List<String> ports,
        List<String> volumes,
        List<String> mounts
    ) {
        super(interactive, tty, timeout, signal, name, environments, ports, volumes, mounts);
        this.detached = detached;
        this.remove = remove;
        this.image = image;
        this.memory = memory;
        this.cpus = cpus;
    }

    public boolean isDetached() {
        return detached;
    }

    public void setDetached(boolean detached) {
        this.detached = detached;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCpus() {
        return cpus;
    }

    public void setCpus(String cpus) {
        this.cpus = cpus;
    }

}