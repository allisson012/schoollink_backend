package com.example.schollink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.Presenca;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    boolean existsByAlunoAndHorarioAula(Aluno aluno, HorarioAula horarioAula);

    List<Presenca> findByHorarioAulaId(Long horarioAulaId);

    Optional<Presenca> findByAlunoAndHorarioAula(Aluno aluno, HorarioAula horarioAula);
}
