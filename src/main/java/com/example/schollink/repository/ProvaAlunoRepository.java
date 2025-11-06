package com.example.schollink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Prova;
import com.example.schollink.model.ProvaAluno;

@Repository
public interface ProvaAlunoRepository extends JpaRepository<ProvaAluno, Long> {
    boolean existsByAlunoIdAlunoAndProvaIdProva(Long idAluno, Long idProva);
    List<ProvaAluno> findByProvaAndAluno(Prova prova,Aluno aluno);
    List<ProvaAluno> findByAlunoIdAluno(Long idAluno);
}
