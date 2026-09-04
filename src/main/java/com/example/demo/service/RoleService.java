package com.example.demo.service;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRole(Long id){
        return roleRepository.findById(id).get();
    }

    public Role criarRole(String name) {
        Role role = roleRepository.findByName(name).orElseGet(() -> {
            Role novaRole = new Role(null, name);
            System.out.println("Role " + name + " criada.");
            return roleRepository.save(novaRole);
        });
        return role;
    }


    public Role addPermissions(Long id, Set<Permission> permissions) {
        Role role = findRole(id);
        role.getPermissions().addAll(permissions);
        return roleRepository.save(role);
    }

    public void deleteRole(Long id){
        if(!findRole(id).getName().equals("ADMIN")){
            roleRepository.deleteById(id);
        }
    }
}
