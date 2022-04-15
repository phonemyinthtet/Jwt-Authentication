package com.example.jwtprojectdemo.controller;

import com.example.jwtprojectdemo.entity.Role;
import com.example.jwtprojectdemo.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/role")
    public String roleIndex(){
        return "Hello : Welocome to Our Role";
    }
    @PostMapping("/role/create")
    public Role createRole(@RequestBody Role role){
        return roleService.createNewRole(role);
    }

}
