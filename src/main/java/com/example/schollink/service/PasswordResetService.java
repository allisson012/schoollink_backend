package com.example.schollink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.AlterarSenhaDto;
import com.example.schollink.model.User;
import com.example.schollink.repository.UserRepository;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordResetMemoryService passwordResetMemoryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;

    public boolean alterarSenha(AlterarSenhaDto dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        if (!passwordService.compararSenha(dto.getSenhaAtual(), user.getHash(), user.getSalt())) {
            return false;
        }

        user.setHash(passwordService.gerarHash(dto.getNovaSenha(), user.getSalt()));
        userRepository.save(user);
        return true;
    }

    public boolean esquecerSenha(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }
        passwordResetMemoryService.gerarCodigo(email);
        return true;
    }

    public boolean alterarSenhaPeloCodigo(String email, String novaSenha, String codigo) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        boolean codigoValido = passwordResetMemoryService.validarCodigo(user.getEmail(), codigo);
        if (codigoValido) {
            user.setHash(passwordService.gerarHash(novaSenha, user.getSalt()));
            userRepository.save(user);
        } else {
            return false;
        }
        return true;
    }
}
