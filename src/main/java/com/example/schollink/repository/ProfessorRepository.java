package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.schollink.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByUser_Id(Long idUser);
}
