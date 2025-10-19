package com.example.schollink.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Funcionario;
import com.example.schollink.model.Ponto;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, Long> {
    boolean existsByDataAndFuncionario(LocalDate data, Funcionario funcionario);
    Optional<Ponto> findByDataAndFuncionario(LocalDate data, Funcionario funcionario);
}
