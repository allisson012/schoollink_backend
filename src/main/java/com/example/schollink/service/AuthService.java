package com.example.schollink.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.AlterarSenhaDto;
import com.example.schollink.Dto.CodigoEmail;
import com.example.schollink.model.Admin;
import com.example.schollink.model.User;
import com.example.schollink.model.UserRole;
import com.example.schollink.repository.AdminRepository;
import com.example.schollink.repository.UserRepository;

@Service
public class AuthService {
    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private PasswordResetMemoryService passwordResetMemoryService;

    public Optional<User> autenticarProfessor(String email, String senha) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean senhaValida = passwordService.compararSenha(senha, user.getHash(), user.getSalt());
            if (senhaValida && user.getUserRole() != null && user.getUserRole().equals(UserRole.PROFESSOR)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> autenticarAluno(String email, String senha) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean senhaValida = passwordService.compararSenha(senha, user.getHash(), user.getSalt());
            if (senhaValida && user.getUserRole() != null && user.getUserRole().equals(UserRole.ALUNO)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<Admin> autenticarAdmin(String email, String senha) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            boolean senhaValida = passwordService.compararSenha(senha, admin.getHash(), admin.getSalt());
            if (senhaValida) {
                return Optional.of(admin);
            }
        }
        return Optional.empty();
    }

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

    public boolean esquecerSenha(Long idUser) {
        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        passwordResetMemoryService.gerarCodigo(user.getEmail());
        return true;
    }

    public boolean alterarSenhaPeloCodigo(Long idUser, String novaSenha, String codigo){
        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        boolean codigoValido = passwordResetMemoryService.validarCodigo(user.getEmail(), codigo);
        if(codigoValido){
            user.setHash(passwordService.gerarHash(novaSenha, user.getSalt()));
            userRepository.save(user);
        }else{
            return false;
        }
        return true;
    }
}
