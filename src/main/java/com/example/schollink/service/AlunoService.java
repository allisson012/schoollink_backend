package com.example.schollink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.StatusMatricula;
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

    public Aluno editarAluno(Aluno alunoNovo, Long id) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        Aluno alunoExistente = alunoOpt.get();

        if (alunoNovo.getMatricula() != null && !alunoNovo.getMatricula().isBlank()) {
            alunoExistente.setMatricula(alunoNovo.getMatricula());
        }
        if (alunoNovo.getTelefoneResponsavel() != null && !alunoNovo.getTelefoneResponsavel().isBlank()) {
            alunoExistente.setTelefoneResponsavel(alunoNovo.getTelefoneResponsavel());
        }
        if (alunoNovo.getStatusMatricula() != null) {
            alunoExistente.setStatusMatricula(alunoNovo.getStatusMatricula());
        }

        if (alunoNovo.getUser() != null) {
            User userNovo = alunoNovo.getUser();
            User userExistente = alunoExistente.getUser();

            if (userNovo.getNome() != null && !userNovo.getNome().isBlank()) {
                userExistente.setNome(userNovo.getNome());
            }
            if (userNovo.getEmail() != null && !userNovo.getEmail().isBlank()) {
                userExistente.setEmail(userNovo.getEmail());
            }
            if (userNovo.getCpf() != null && !userNovo.getCpf().isBlank()) {
                userExistente.setCpf(userNovo.getCpf());
            }
            if (userNovo.getTelefone() != null && !userNovo.getTelefone().isBlank()) {
                userExistente.setTelefone(userNovo.getTelefone());
            }
            if (userNovo.getGenero() != null && !userNovo.getGenero().isBlank()) {
                userExistente.setGenero(userNovo.getGenero());
            }
            userRepository.save(userExistente);
        }
        return alunoRepository.save(alunoExistente);
    }

    public void excluirAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        
        if(aluno.getStatusMatricula().equals(StatusMatricula.ATIVA)){
            aluno.setStatusMatricula(StatusMatricula.INATIVA);
            alunoRepository.save(aluno);
        }
    }
}
