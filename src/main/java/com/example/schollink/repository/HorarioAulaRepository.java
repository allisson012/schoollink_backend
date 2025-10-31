package com.example.schollink.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Disciplina;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.Turma;
import com.example.schollink.model.TurmaDisciplina;

import java.util.List;

@Repository
public interface HorarioAulaRepository extends JpaRepository<HorarioAula, Long> {
        boolean existsByTurmaDisciplina_TurmaAndTurmaDisciplina_DisciplinaAndData(
                        Turma turma, Disciplina disciplina, LocalDate data);

        List<HorarioAula> findByDataAndTurmaDisciplina_Turma(LocalDate data, Turma turma);

        List<HorarioAula> findByDataAndTurmaDisciplina_Professor_Id(LocalDate data, Long professorId);

        List<HorarioAula> findByTurmaDisciplina(TurmaDisciplina turmaDisciplina);

        List<HorarioAula> findByDataAndTurmaDisciplina(LocalDate data, TurmaDisciplina turmaDisciplina);

        @Query("SELECT h FROM HorarioAula h " +
                        "WHERE h.data BETWEEN :inicio AND :fim " +
                        "AND h.turmaDisciplina.professor.id = :idProfessor")
        List<HorarioAula> findByDataBetweenAndProfessorId(
                        @Param("inicio") LocalDate inicio,
                        @Param("fim") LocalDate fim,
                        @Param("idProfessor") Long idProfessor);
        // List<HorarioAula> findByDataBetweenAndTurmaDisciplina_Professor_Id(
        // LocalDate inicioSemana, LocalDate fimSemana, Long professorId);

}
