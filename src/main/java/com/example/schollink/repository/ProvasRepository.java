package com.example.schollink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Prova;
import com.example.schollink.model.TipoProva;
import com.example.schollink.model.TurmaDisciplina;

@Repository
public interface ProvasRepository extends JpaRepository<Prova, Long> {

    Optional<Prova> findByAlunoAndTurmaDisciplinaAndTipo(
            Aluno aluno,
            TurmaDisciplina turmaDisciplina,
            TipoProva tipo);

    List<Prova> findByTurmaDisciplina(TurmaDisciplina turmaDisciplina);

    List<Prova> findByAlunoIdAlunoAndTurmaDisciplinaId(Long idAluno, Long idTurmaDisciplina);

    @Query("SELECT p.aluno.id, AVG(p.nota) FROM Prova p WHERE p.turmaDisciplina.id = :turmaDisciplinaId GROUP BY p.aluno.id")
    List<Object[]> findMediasPorTurmaDisciplina(@Param("turmaDisciplinaId") Long turmaDisciplinaId);
}
