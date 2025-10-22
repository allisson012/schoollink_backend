package com.example.schollink.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Disciplina;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.Turma;
import java.util.List;

@Repository
public interface HorarioAulaRepository extends JpaRepository<HorarioAula, Long> {
    boolean existsByTurmaAndDisciplinaAndData(Turma turma, Disciplina disciplina, LocalDate data);

    List<HorarioAula> findByDataAndTurma(LocalDate data, Turma turma);

    List<HorarioAula> findByDataAndProfessorId(LocalDate data, Long professorId);

}
