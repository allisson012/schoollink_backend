package com.example.schollink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Professor;
import com.example.schollink.model.Turma;
import com.example.schollink.model.TurmaDisciplina;

public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {
    Optional<TurmaDisciplina> findByTurmaAndDisciplinaAndProfessor(Turma turma, Disciplina disciplina,
            Professor professor);

    List<TurmaDisciplina> findByTurma(Turma turma);

    List<TurmaDisciplina> findByProfessor(Professor professor);

    List<TurmaDisciplina> findByProfessorUserId(Long userId);

    List<TurmaDisciplina> findByTurmaIdAndProfessorId(Long turmaId, Long professorId);
}
