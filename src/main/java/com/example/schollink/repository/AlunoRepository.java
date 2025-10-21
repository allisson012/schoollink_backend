package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;

import java.util.List;
import java.util.Optional;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    //public void createAluno(Long idUser, String matricula);
    Optional<Aluno> findByUserId(Long userId);
    Optional<Aluno> findByRfid(String rfid);
    List<Aluno> findByUserEmailContainingIgnoreCase(String email);
    List<Aluno> findByMatriculaContainingIgnoreCase(String matricula);
    List<Aluno> findByUserNomeContainingIgnoreCase(String nome);

 }
