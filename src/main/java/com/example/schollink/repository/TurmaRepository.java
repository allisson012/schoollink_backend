package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

}
