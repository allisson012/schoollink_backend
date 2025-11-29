package com.example.schollink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByUser_Id(Long idUser);
    Optional<Professor> findByFuncionario_IdFuncionario(Long idFuncionario);
    List<Professor> findByUserNomeContainingIgnoreCase(String nome);
}
