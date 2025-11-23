package com.example.schollink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Conversa;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    @Query("SELECT c FROM Conversa c WHERE c.aluno = :aluno")
    Optional<Conversa> findByAluno(@Param("aluno") Aluno aluno);
}
