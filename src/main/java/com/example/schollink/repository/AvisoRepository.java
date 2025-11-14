package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aviso;
import com.example.schollink.model.Turma;

import java.util.List;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long>{
    List<Aviso> findByTurmaDisciplina_Turma(Turma turma);
}
