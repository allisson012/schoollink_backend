package com.example.schollink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Professor;
import com.example.schollink.repository.DisciplinaRepository;
import com.example.schollink.repository.ProfessorRepository;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    public boolean cadastrarDisciplina(Disciplina disciplina, Long idProfessor){
        Optional<Professor> professorOpt = professorRepository.findById(idProfessor);
        if(professorOpt.isPresent()){
            Professor professor = professorOpt.get();
            disciplina.setProfessor(professor);
            disciplinaRepository.save(disciplina);
            return true;
        }else{
            return false;
        }
    }
}
