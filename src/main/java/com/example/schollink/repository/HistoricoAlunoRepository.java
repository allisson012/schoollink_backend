package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.schollink.model.HistoricoAluno;

import java.util.Optional;

public interface HistoricoAlunoRepository extends JpaRepository<HistoricoAluno, Long> {
    Optional<HistoricoAluno> findByAlunoIdAluno(Long idAluno);
}

