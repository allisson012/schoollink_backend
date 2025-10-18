package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.HorarioAula;

@Repository
public interface HorarioAulaRepository extends JpaRepository<HorarioAula, Long> {

}
