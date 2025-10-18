package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schollink.model.Presenca;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

}
