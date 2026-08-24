package com.example.demo.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.demo.config.DockerHost;
import com.example.demo.entity.Usuario;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.JsonNodeType;

@Repository
public class JsonUserRepository implements UserRepository {

    private final File filePath;

    public JsonUserRepository(@Value("${app.data.users-file}") String file) {
        this.filePath = new File(file);
    }

    @Override
    public void deleteAll() {
        ObjectMapper mapper = new ObjectMapper();
        List<Usuario> usuarios = new ArrayList<>();

        if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
            System.out.println("Acessando connection.json em: " + filePath.getAbsolutePath());
            mapper.writeValue(filePath, usuarios);
        }
    }

    @Override
    public void deleteById(UUID id) {
        try {
            List<Usuario> usuarios = findAll();
            List<Usuario> savedUsuarios = new ArrayList<>();

            for (int i = 0; i < usuarios.size(); i++) {
                if (!usuarios.get(i).getId().equals(id)) {
                    savedUsuarios.add(usuarios.get(i));
                }
            }
            deleteAll();
            saveAll(savedUsuarios);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        ObjectMapper mapper = new ObjectMapper();
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();

        if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
            System.out.println("Acessando users.json em: " + filePath.getAbsolutePath());
            usuarios = mapper.readValue(filePath, new TypeReference<List<Usuario>>() {
            });
        }
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(id)) {
                return Optional.of(usuarios.set(i, usuario));
            }
        }
        return Optional.empty();
    }

    @Override
    public Usuario findByUsername(String name) {
        ObjectMapper mapper = new ObjectMapper();
        List<Usuario> usuarios = new ArrayList<>();

        if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) { 
            System.out.println("Acessando users.json em: " + filePath.getAbsolutePath());
            usuarios = mapper.readValue(filePath, new TypeReference<List<Usuario>>() {});
        }
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(name)) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    @Override
    public Usuario save(Usuario usuario) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Usuario> usuarios = new ArrayList<>();

            if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
                System.out.println("Acessando users.json em: " + filePath.getAbsolutePath());
                usuarios = mapper.readValue(filePath, new TypeReference<List<Usuario>>() {
                });
                usuarios.add(usuario);
            } else {
                System.out.println("connection.json esta vazio!");
                usuarios.add(usuario);
            }
            mapper.writeValue(filePath, usuario);
            return usuario;

        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario update(Usuario usuario) {
        if(findById(usuario.getId()).isEmpty()) return findById(usuario.getId()).get();
        deleteById(usuario.getId());
        return save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Usuario> usuarios = new ArrayList<>();

            if (mapper.readTree(filePath).getNodeType() == JsonNodeType.ARRAY) {
                System.out.println("Acessando users.json em: " + filePath.getAbsolutePath());
                usuarios = mapper.readValue(filePath, new TypeReference<List<Usuario>>() {
                });
            }
            return usuarios;
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<Usuario> usuarios) {
        for (int i = 0; i < usuarios.size(); i++) {
            save(usuarios.get(i));
        }
    }
}
