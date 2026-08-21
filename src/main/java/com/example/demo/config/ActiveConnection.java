package com.example.demo.config;

import org.springframework.stereotype.Component;

@Component
public class ActiveConnection {

    private DockerHost connection;

    public DockerHost getConnection() {
        return connection;
    }

    public void setConnection(DockerHost connection) {
        this.connection = connection;
    }

    public void clear() {
        this.connection = null;
    }

    public boolean isActive() {
        return connection != null;
    }
}
