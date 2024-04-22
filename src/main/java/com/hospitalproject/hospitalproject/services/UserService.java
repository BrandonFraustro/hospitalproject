package com.hospitalproject.hospitalproject.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalproject.hospitalproject.entities.Users;
import com.hospitalproject.hospitalproject.repositories.UserRepository;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import org.slf4j.Logger;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<Users> getUser(Long idUser) {
        try {
            return userRepository.findById(idUser);
        } catch(Exception e) {
            logger.error("UserSevice:getUser ", e);
            throw e;
        }
    }

    public Users addUser(Users userContent) {
        try {
            return userRepository.save(userContent);
        } catch(Exception e) {
            logger.error("UserSevice:addUser", e);

            throw e;
        }
    }

    public Users getUserCredentials(String username, String password) {
        try {
            Optional<Users> uOptional = userRepository.findByUsername(username);

            if(uOptional.isPresent()) {
                Users user = uOptional.get();
                Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                
                if(argon2.verify(user.getPassword(), password)) {
                    return uOptional.get();
                }
                return null;
            }
            return null;
        } catch(Exception e) {
            logger.error("UserSevice:addUser", e);

            throw e;
        }
    }

    public boolean deleteUser(Long idUser) {
        boolean deleted = false;
        try {
            userRepository.deleteById(idUser);
            deleted = true;
            return deleted;
        } catch(Exception e) {
            logger.error("UserSevice:deleteUser", e);
            throw e;
        }
    }

    public Users updateUser(Long idUser, Users userContent) {
        Users userFounded = userRepository.findById(idUser).get();
        userFounded.setName(userContent.getName());
        userFounded.setUsername(userContent.getUsername());
        userFounded.setPassword(userContent.getPassword());
        
        try {
            return userRepository.save(userFounded);
        } catch(Exception e) {
            logger.error("UserSevice:updateUser", e);
            throw e;
        }
    }
}
