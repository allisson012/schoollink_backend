package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.ProvaAluno;

@Repository
public interface ProvaAlunoRepository extends JpaRepository<ProvaAluno, Long> {
    boolean existsByAlunoIdAlunoAndProvaIdProva(Long idAluno, Long idProva);
}
