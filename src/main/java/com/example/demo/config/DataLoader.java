package com.example.demo.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Host;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.segurity.SecurityConfig;

@Configuration
public class DataLoader {
    // isso no futuro sera configurado para uma variavel de ambiente para a primeira
    // configuraçao da aplicação
    String ADMIN_PASS = "123456";
    String ADMIN_NAME = "admin";
    String TESTE_NAME = "user";
    String HOST_NAME = "admin";
    String HOST_IP = "admin";
    int HOST_PORT = 0;

    @Bean
    public CommandLineRunner load(
            HostRepository hostRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            UsuarioRepository usuarioRepository,
            SecurityConfig securityConfig) {
        return args -> {
            // Criptografa a senha
            String PASS = securityConfig.passwordEncoder().encode(ADMIN_PASS);

            Host host = hostRepository.findByName(HOST_NAME).orElseGet(() -> {
                Host novoHost = new Host(null, HOST_NAME, HOST_IP, HOST_PORT, true);
                System.out.println("Host padrão criado.");
                return hostRepository.save(novoHost);
            });

            // Containers
            Permission read = criarPermission(permissionRepository, "CONTAINER_READ");
            Permission create = criarPermission(permissionRepository, "CONTAINER_CREATE");
            Permission start = criarPermission(permissionRepository, "CONTAINER_START");
            Permission stop = criarPermission(permissionRepository, "CONTAINER_STOP");
            Permission restart = criarPermission(permissionRepository, "CONTAINER_RESTART");
            Permission delete = criarPermission(permissionRepository, "CONTAINER_DELETE");

            // Connections
            Permission createConn = criarPermission(permissionRepository, "CONNECTION_CREATE");
            Permission readConn = criarPermission(permissionRepository, "CONNECTION_READ");
            Permission deleteConn = criarPermission(permissionRepository, "CONNECTION_DELETE");
            Permission updateConn = criarPermission(permissionRepository, "CONNECTION_UPDATE");

            Role admin = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role(null, "ADMIN");
                        System.out.println("Role ADMIN criada.");
                        return roleRepository.save(role);
                    });

            Role user = roleRepository.findByName("USER")
                    .orElseGet(() -> {
                        Role role = new Role(null, "USER");
                        System.out.println("Role USER criada.");
                        return roleRepository.save(role);
                    });

            admin.getPermissions().addAll(Set.of(read,create,start,stop,restart,delete,createConn,readConn,deleteConn,updateConn));
            roleRepository.save(admin);
            
            user.getPermissions().addAll(Set.of(read, readConn));
            roleRepository.save(user);

            if (!usuarioRepository.findByUsername(ADMIN_NAME).isPresent()) {
                Usuario usuario = new Usuario(null, ADMIN_NAME, PASS, admin, host.getId());
                usuarioRepository.save(usuario);
                System.out.println("Novo ADMIN criado.");
            }
            if (!usuarioRepository.findByUsername(TESTE_NAME).isPresent()) {
                Usuario usuario = new Usuario(null, TESTE_NAME, PASS, user, host.getId());
                usuarioRepository.save(usuario);
                System.out.println("Novo usuario criado.");
            }
        };
    }

    private Permission criarPermission(PermissionRepository repository, String nome) {
        return repository.findByName(nome)
                .orElseGet(() -> {
                    Permission permission = new Permission(nome);
                    System.out.println("Permission " + nome + " criada.");
                    return repository.save(permission);
                });
    }

}
