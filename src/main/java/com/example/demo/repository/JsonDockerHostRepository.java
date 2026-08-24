package com.example.demo.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DockerHost;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.JsonNodeType;

@Deprecated
@Repository
public class JsonDockerHostRepository {

    private final File filePath;

    public JsonDockerHostRepository(@Value("${app.data.connections-file}") String file) {
        this.filePath = new File(file);
    }

    
    public DockerHost save(DockerHost connection) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<DockerHost> connections = new ArrayList<>();

            if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
                System.out.println("Acessando connection.json em: " + filePath.getAbsolutePath());
                connections = mapper.readValue(filePath, new TypeReference<List<DockerHost>>() {
                });
                connections.add(connection);
            } else {
                System.out.println("connection.json esta vazio!");
                connections.add(connection);
            }
            mapper.writeValue(filePath, connections);
            return connection;

        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<DockerHost> connections) {
            for(int i = 0;i < connections.size();i++){
                save(connections.get(i));
            }
    }

    
    public List<DockerHost> findAll() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<DockerHost> connections = new ArrayList<>();

            if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
                System.out.println("Acessando connection.json em: " + filePath.getAbsolutePath());
                connections = mapper.readValue(filePath, new TypeReference<List<DockerHost>>() {});
            }
            return connections;
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    
    public Optional<DockerHost> findById(UUID id) {
        ObjectMapper mapper = new ObjectMapper();
        List<DockerHost> connections = new ArrayList<>();
        DockerHost connection = new DockerHost();

        if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
            System.out.println("Acessando connection.json em: " + filePath.getAbsolutePath());
            connections = mapper.readValue(filePath, new TypeReference<List<DockerHost>>() {
            });
        }
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getId().equals(id)) {
                return Optional.of(connections.set(i, connection));
            }
        }
        return Optional.empty();
    }

    
    public void deleteById(UUID id) {
        try {
            List<DockerHost> connections = findAll();
            List<DockerHost> savedConnections = new ArrayList<>();

            for(int i = 0; i < connections.size();i++){
                if(!connections.get(i).getId().equals(id)) savedConnections.add(connections.get(i));
            }
            deleteAll();
            saveAll(savedConnections);
        }catch(JacksonException e){
            throw new RuntimeException(e);
        }
    }

    
    public void deleteAll() {
        ObjectMapper mapper = new ObjectMapper();
        List<DockerHost> connections = new ArrayList<>();

        if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
            System.out.println("Acessando connection.json em: " + filePath.getAbsolutePath());
            mapper.writeValue(filePath, connections);
        }
    }

    
    public DockerHost update(DockerHost connection){
        if(findById(connection.getId()).isEmpty()) return findById(connection.getId()).get();
        deleteById(connection.getId());
        return save(connection);
    }

}
