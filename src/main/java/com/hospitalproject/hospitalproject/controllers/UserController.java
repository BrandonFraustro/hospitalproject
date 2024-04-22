package com.hospitalproject.hospitalproject.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospitalproject.hospitalproject.entities.Users;
import com.hospitalproject.hospitalproject.services.UserService;
import com.hospitalproject.hospitalproject.util.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.slf4j.Logger;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JWTUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @GetMapping("/users")
    public List<Users> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{idUser}")
    public Optional<Users> getUserById(@PathVariable Long idUser) {
        try {
            return userService.getUser(idUser);
        } catch(Exception e) {
            logger.error("UserController:getUserById", e);
            throw e;
        }
    }
    
    @PostMapping("/users")
    public Users addUser(@RequestBody Users userContent) {
        
        try {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1, 1024, 1, userContent.getPassword());
            userContent.setPassword(hash);
            
            return userService.addUser(userContent);
        } catch(Exception e) {
            logger.error("UserController:addUser", e);
            throw e;
        }
    }
    
    @DeleteMapping("/users/{idUser}")
    public String deleteUser(@PathVariable Long idUser) {
        boolean deleted = userService.deleteUser(idUser);

        if (deleted) {
            return "User deleted correctly";
        } else {
            return "Error deleting this user";
        }
    }

    @PutMapping("/users/{idUser}")
    public Users updateUser(@PathVariable Long idUser, @RequestBody Users userContent) {
        return userService.updateUser(idUser, userContent);
    }
}
