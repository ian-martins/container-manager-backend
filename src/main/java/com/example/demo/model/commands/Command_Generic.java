package com.example.demo.model.commands;



import java.util.ArrayList;
import java.util.List;

public class Command_Generic {
    private boolean interactive;
    private boolean tty;
    
    private String timeout;
    private String signal;
    private String name;
    
    private List<String> environments = new ArrayList<>();
    private List<String> ports = new ArrayList<>();
    private List<String> volumes = new ArrayList<>();
    private List<String> mounts = new ArrayList<>();

    public Command_Generic() {
    }

    public Command_Generic(
        boolean interactive, 
        boolean tty, 
        String timeout, 
        String signal, 
        String name,
        List<String> environments,
        List<String> ports,
        List<String> volumes,
        List<String> mounts
    ) {
        this.interactive = interactive;
        this.tty = tty;
        this.timeout = timeout;
        this.signal = signal;
        this.name = name;
        this.environments = environments;
        this.ports = ports;
        this.volumes = volumes;
        this.mounts = mounts;
    }

    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public boolean isTty() {
        return tty;
    }

    public void setTty(boolean tty) {
        this.tty = tty;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<String> environments) {
        this.environments = environments;
    }

    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }

    public List<String> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<String> volumes) {
        this.volumes = volumes;
    }

    public List<String> getMounts() {
        return mounts;
    }

    public void setMounts(List<String> mounts) {
        this.mounts = mounts;
    }
       
    
}
