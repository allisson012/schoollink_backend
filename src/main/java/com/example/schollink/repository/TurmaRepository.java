package com.example.schollink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
 List<Aluno> findByIdTurma(Long idTurma);
}
