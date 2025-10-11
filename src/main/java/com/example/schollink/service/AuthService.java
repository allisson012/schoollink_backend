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

    @Autowired
    private PasswordService passwordService;

    public Optional<User> autenticarUsuario(String email, String senha) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean senhaValida = passwordService.compararSenha(senha, user.getHash(), user.getSalt());
            if (senhaValida) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
