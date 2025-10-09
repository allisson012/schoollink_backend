package com.example.schollink.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.User;
import com.example.schollink.repository.UserRepository;

@Service
public class AuthService {
    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private UserRepository userRepository;

     public Optional<User> autenticarUsuario(String email) {
        return userRepository.findByEmail(email);
    }
}
