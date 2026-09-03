package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Host;
import com.example.demo.entity.Role;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.segurity.SecurityConfig;

@Configuration
public class DataLoader {

    String ADMIN_PASS = "123456";
    String ADMIN_NAME = "admin";
    String HOST_NAME = "admin";
    String HOST_IP = "admin";
    int HOST_PORT = 0;

    @Bean
    public CommandLineRunner load(HostRepository hostRepository, RoleRepository roleRepository,
            UsuarioRepository usuarioRepository, SecurityConfig securityConfig) {
        return args -> {
            String PASS = securityConfig.passwordEncoder().encode(ADMIN_PASS);

            Role role = new Role(null, "ADMIN");
            Host host = new Host(null, HOST_NAME, HOST_IP, HOST_PORT, true);

            if (!hostRepository.findByName(HOST_NAME).isPresent()) {
                hostRepository.save(host);
                System.out.println("Host padrão criado.");
            }
            if (!roleRepository.findByName("ADMIN").isPresent()) {
                roleRepository.save(role);
                System.out.println("Role ADMIN criado.");
            }

            if(!usuarioRepository.findByUsername(ADMIN_NAME).isPresent()){
                Usuario usuario = new Usuario(null, ADMIN_NAME, PASS, roleRepository.findByName("ADMIN").get(), hostRepository.findByName(HOST_NAME).get().getId());
                usuarioRepository.save(usuario);
                System.out.println("Novo ADMIN criado.");
            }

        };
    }
}

/*
 [ERROR: null value in column "name" of relation "usuarios" violates not-null constraint
  Detalhe: Failing row contains
(8cff086c-fcf5-4e89-8327-2f3882e5d843, 99b2bef4-be9a-4ec7-8639-a098d487c0cc, $2a$10$1pFWzWKfKF7Egf/m4bnqwOm900nznr7TGZKCx8HHmvK3VtDr.sjwG, admin, 616bc7be-f0e7-48b7-ac22-0e830af91903, null).] 
insert into usuarios (docker_host_id,password,role_id,username,id) values (?,?,?,?,?)];
 SQL [insert into usuarios (docker_host_id,password,role_id,username,id) values (?,?,?,?,?)]; constraint [name]

*/
