package com.example.schollink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.User;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.UserRepository;

@Service
public class AlunoService {
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    public void cadastrarAluno(User user, Aluno aluno, String senha) {
        byte salt[] = passwordService.gerarSalt();
        byte hash[] = passwordService.gerarHash(senha, salt);
        user.setSalt(salt);
        user.setHash(hash);
        User userCreate = userRepository.save(user);
        aluno.setUser(userCreate);
        alunoRepository.save(aluno);
    }
}
