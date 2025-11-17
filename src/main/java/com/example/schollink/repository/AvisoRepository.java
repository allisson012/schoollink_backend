package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Aviso;
import java.util.List;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long>{
    List<Aviso> findByTurmaId(Long turmaId);
    List<Aviso> findByTurmaIdOrTurmaIsNull(Long turmaId);
}
