package com.example.demo.config;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Host;
import com.example.demo.entity.Role;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.segurity.SecurityConfig;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner addDockerHost(HostRepository dockerHostRepository) {
        return args -> {
            String name = "WSL";

            Optional<Host> host = dockerHostRepository.findByName(name);

            if (!host.isPresent()) {
                Host dHost = new Host(null, name, "localhost", 5000, true);
                dockerHostRepository.save(dHost);
                System.out.println("Novo host criado.");
            }
        };
    }

    @Bean
    public CommandLineRunner addUsuario(UsuarioRepository usuarioRepository, HostRepository dockerHostRepository, SecurityConfig securityConfig) {
        return args -> {
            String admin = "admin";
            String operator = "operator";
            String viewer = "viewer";
            String pass = securityConfig.passwordEncoder().encode("123456");
            Optional<Host> host = dockerHostRepository.findByName("WSL");
            Optional<Usuario> adminOpt = usuarioRepository.findByUsername(admin);
            Optional<Usuario> operatorOpt = usuarioRepository.findByUsername(operator);
            Optional<Usuario> viewerOpt = usuarioRepository.findByUsername(viewer);
            
            if (!adminOpt.isPresent() && host.isPresent()) {
                Usuario nUsuario = new Usuario(null, admin, pass, Role.ADMIN, host.get().getId());
                usuarioRepository.save(nUsuario);
                System.out.println("Novo ADMIN criado.");
            }
            if (!operatorOpt.isPresent() && host.isPresent()) {
                Usuario nUsuario = new Usuario(null, operator, pass, Role.OPERATOR, host.get().getId());
                usuarioRepository.save(nUsuario);
                System.out.println("Novo OPERATOR criado.");
            }
            if (!viewerOpt.isPresent() && host.isPresent()) {
                Usuario nUsuario = new Usuario(null, viewer, pass, Role.VIEWER, host.get().getId());
                usuarioRepository.save(nUsuario);
                System.out.println("Novo VIEWER criado.");
            }
            
            
            
            
        };
    }

}
