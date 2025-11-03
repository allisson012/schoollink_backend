package com.example.schollink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.HorarioFixo;
import com.example.schollink.model.Turma;

@Repository
public interface HorarioFixoRepository extends JpaRepository<HorarioFixo, Long> {
    List<HorarioFixo> findByTurma(Turma turma);
}
