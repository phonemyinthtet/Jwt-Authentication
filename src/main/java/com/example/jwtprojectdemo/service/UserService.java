package com.example.jwtprojectdemo.service;

import com.example.jwtprojectdemo.dao.RoleRepository;
import com.example.jwtprojectdemo.dao.UserRepository;
import com.example.jwtprojectdemo.entity.Role;
import com.example.jwtprojectdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void initCreateUserAndRole(){
        Role role1=new Role();
        role1.setName("Admin");
        roleRepository.save(role1);
        Role role2 =new Role();
        role2.setName("User");
        roleRepository.save(role2);

        Set<Role> user1Role = new HashSet<>();
        user1Role.add(role1);

        User user1 = new User();
        user1.setUsername("admin");
        user1.setFirstName("admin");
        user1.setLastName("admin");
        user1.setPassword(passwordEncoder.encode("admin"));
        user1.setRoles(user1Role);
        userRepository.save(user1);

        Set<Role> user2Role = new HashSet<>();
        user1Role.add(role2);

        User user2 = new User();
        user2.setUsername("user");
        user2.setFirstName("user");
        user2.setLastName("user");
        user2.setPassword(passwordEncoder.encode("user"));
        user2.setRoles(user2Role);
        userRepository.save(user2);



    }

}
