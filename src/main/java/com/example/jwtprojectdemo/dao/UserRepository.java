package com.example.jwtprojectdemo.dao;

import com.example.jwtprojectdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserByUsername(String username);

}
