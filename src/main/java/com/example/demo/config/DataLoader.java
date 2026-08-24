package com.example.demo.config;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.DockerHost;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.DockerHostRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.segurity.SecurityConfig;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner addDockerHost(DockerHostRepository dockerHostRepository) {
        return args -> {
            String name = "WSL";

            Optional<DockerHost> host = dockerHostRepository.findByName(name);

            if (!host.isPresent()) {
                DockerHost dHost = new DockerHost(null, name, "localhost", 5000, true);
                dockerHostRepository.save(dHost);
                System.out.println("Novo host criado.");
            }
        };
    }

    @Bean
    public CommandLineRunner addUsuario(UsuarioRepository usuarioRepository, DockerHostRepository dockerHostRepository, SecurityConfig securityConfig) {
        return args -> {
            String admin = "admin";
            String pass = securityConfig.passwordEncoder().encode("123456");
            Optional<DockerHost> host = dockerHostRepository.findByName("WSL");
            Optional<Usuario> adminOpt = usuarioRepository.findByUsername(admin);
            if (!adminOpt.isPresent() && host.isPresent()) {
                Usuario nUsuario = new Usuario(null, admin, pass, "ADMIN", host.get().getId());
                usuarioRepository.save(nUsuario);
                System.out.println("Novo ADMIN criado.");
            }
        };
    }

}
