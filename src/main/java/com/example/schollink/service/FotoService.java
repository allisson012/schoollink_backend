package com.example.schollink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.User;
import com.example.schollink.repository.UserRepository;

@Service
public class FotoService {
    @Autowired
    private UserRepository userRepository;

    public boolean salvarFoto(Long idUser, String caminhoFoto) {
        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        user.setCaminhoFoto(caminhoFoto);
        userRepository.save(user);
        return true;
    }
}
