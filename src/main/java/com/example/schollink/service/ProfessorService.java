package com.example.schollink.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schollink.model.Professor;
import com.example.schollink.model.User;
import com.example.schollink.repository.ProfessorRepository;
import com.example.schollink.repository.UserRepository;
import com.example.schollink.service.PasswordService;
import com.example.schollink.model.Turno;

@Service
public class ProfessorService {
    @Autowired private PasswordService passwordService;
    @Autowired private UserRepository userRepository;
    @Autowired private ProfessorRepository professorRepository;

    public void cadastrarProfessor(User user, Professor professor, String senha) {
        byte salt[] = passwordService.gerarSalt();
        byte hash[] = passwordService.gerarHash(senha, salt);
        user.setSalt(salt);
        user.setHash(hash);
        User savedUser = userRepository.save(user);
        professor.setUser(savedUser);
        professorRepository.save(professor);
    }

    public void editarProfessor(Professor novo, Long id) {
        Optional<Professor> profOpt = professorRepository.findById(id);
        if (profOpt.isEmpty()){
            throw new RuntimeException("Professor não encontrado");
        }

        Professor existente = profOpt.get();

        if (novo.getFormacaoAcademica() != null && !novo.getFormacaoAcademica().isBlank()) {
            existente.setFormacaoAcademica(novo.getFormacaoAcademica());
        }
        if (novo.getRegistroProfissional() != null && !novo.getRegistroProfissional().isBlank()) {
            existente.setRegistroProfissional(novo.getRegistroProfissional());
        }
        if (novo.getCargaHorariaSem() != 0) {
            existente.setCargaHorariaSem(novo.getCargaHorariaSem());
        }
        if (novo.getTurno() != null) {
            existente.setTurno(novo.getTurno());
        }
        if (novo.getSalario() != 0) {
            existente.setSalario(novo.getSalario());
        }
        if (novo.getDataContratacao() != null) {
            existente.setDataContratacao(novo.getDataContratacao());
        }
        if (novo.getUser() != null) {
            User userNovo = novo.getUser();
            User userExistente = existente.getUser();

            if (userNovo.getNome() != null && !userNovo.getNome().isBlank()) {
                userExistente.setNome(userNovo.getNome());
            }
            if (userNovo.getEmail() != null && !userNovo.getEmail().isBlank()) {
                userExistente.setEmail(userNovo.getEmail());
            }

            userRepository.save(userExistente);
        }
    }

    public void deletarProfessor(Long id) {
        Optional<Professor> profOpt = professorRepository.findById(id);
        if (profOpt.isEmpty()){
            throw new RuntimeException("Professor não encontrado");
        }
        Professor existente = profOpt.get();
        professorRepository.delete(existente);
    }
}
