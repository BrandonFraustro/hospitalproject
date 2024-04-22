package com.hospitalproject.hospitalproject.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospitalproject.hospitalproject.entities.UserCredentials;
import com.hospitalproject.hospitalproject.entities.Users;
import com.hospitalproject.hospitalproject.services.UserService;
import com.hospitalproject.hospitalproject.util.JWTUtil;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    JWTUtil jwtUtil;
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    
    @PostMapping("/login")
    public String loginUser(@RequestBody UserCredentials userCredentials) {
        try {
            Users userLogged = userService.getUserCredentials(userCredentials.getUsername(), userCredentials.getPassword());
            if(userLogged != null) {
                String token = jwtUtil.create(String.valueOf(userLogged.getIdUser()), userLogged.getUsername());

                return token;
            } else {
                return "Wrong username or password";
            }
        } catch(Exception e) {
            logger.error("AuthController:loginUser", e);
            throw e;
        }
    }
}
