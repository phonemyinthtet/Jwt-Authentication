package com.example.jwtprojectdemo.service;

import com.example.jwtprojectdemo.dao.RoleRepository;
import com.example.jwtprojectdemo.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createNewRole(Role role){
        return roleRepository.save(role);
    }

}
