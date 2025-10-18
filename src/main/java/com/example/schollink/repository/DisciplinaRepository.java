package com.example.schollink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.model.Disciplina;

@RestController()
@RequestMapping("/disciplina")
public interface DisciplinaRepository extends JpaRepository<Disciplina,Long>{
    
}
