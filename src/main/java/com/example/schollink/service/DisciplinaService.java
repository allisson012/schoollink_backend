package com.example.schollink.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Professor;
import com.example.schollink.repository.DisciplinaRepository;
import com.example.schollink.repository.ProfessorRepository;
import com.example.schollink.Dto.DisciplinaDto;



@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    public boolean cadastrarDisciplina(Disciplina disciplina){
        if(disciplina != null){
            disciplinaRepository.save(disciplina);
            return true;
        }else{
            return false;
        }
    }

    public List<DisciplinaDto> buscarTodasDisciplinas(){
        return disciplinaRepository.findAll()
            .stream()
            .map(d -> new DisciplinaDto(d.getId(), d.getNome()))
            .collect(Collectors.toList());
    }


    public Optional<Disciplina> buscarDisciplinaPorId(Long id){
        return disciplinaRepository.findById(id);
    }
}