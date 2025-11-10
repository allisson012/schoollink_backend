package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.schollink.model.HistoricoAula;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.TurmaDisciplina;

import java.util.List;
import java.util.Optional;

public interface HistoricoAulaRepository extends JpaRepository<HistoricoAula, Long> {
    Optional<HistoricoAula> findByTurmaDisciplina(TurmaDisciplina turmaDisciplina);

    boolean existsByHorarioAula(HorarioAula horarioAula);

    HistoricoAula findByHorarioAula(HorarioAula horarioAula);
}
