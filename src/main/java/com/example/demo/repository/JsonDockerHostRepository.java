package com.example.demo.repository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.demo.config.DockerHost;

import tools.jackson.databind.ObjectMapper;

@Repository
public class JsonDockerHostRepository implements DockerHostRepository {
//@Value("${app.data.connections-file}")

    private final File filePath;

    public JsonDockerHostRepository(@Value("${app.data.connections-file}") String file) {
        this.filePath = new File(file);
    }

    @Override
    public List<DockerHost> findAll()  {
        // ler connections.json
        try{
            ObjectMapper mapper = new ObjectMapper();
            DockerHost dh = mapper.readValue(filePath,DockerHost.class);
            
            return null;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public Optional<DockerHost> findById(UUID id) {
        // procurar no JSON

            return null;

    }

    @Override
    public DockerHost save(DockerHost connection) {
        // salvar no JSON

       


        return connection;
    }

    @Override
    public void deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
   /* JSONObject jsonObj = new JSONObject();
       jsonObj.put("nome", "João");
       jsonObj.put("idade", 30);
       jsonObj.put("cidade", "São Paulo");
       // Converter para String
       System.out.println("JSON como String: " + jsonObj.toString());
       // Acessar valores
       String nome = jsonObj.getString("nome");
       int idade = jsonObj.getInt("idade");
       System.out.println("Nome: " + nome + ", Idade: " + idade);
       */