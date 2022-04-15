package com.example.jwtprojectdemo.controller;

import com.example.jwtprojectdemo.entity.JwtRequest;
import com.example.jwtprojectdemo.entity.JwtResponse;
import com.example.jwtprojectdemo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private JwtService jwtService;

    @PostMapping("/auth")
    public JwtResponse createJWTtoken(@RequestBody JwtRequest jwtRequest){
        return jwtService.createJwtToken(jwtRequest);
    }


}
