package com.example.jwtprojectdemo.controller;

import com.example.jwtprojectdemo.entity.User;
import com.example.jwtprojectdemo.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostConstruct
    public void initCreateUserAndAdmin(){
        userService.initCreateUserAndRole();
    }

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
    @GetMapping("/forUser")
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This Url for User";
    }
    @GetMapping("/forAdmin")
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This Url for Admin";
    }

}
