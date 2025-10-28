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

import jakarta.transaction.Transactional;

import com.example.schollink.Dto.DisciplinaDto;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public void cadastrarDisciplina(Disciplina disciplina) {
        disciplinaRepository.save(disciplina);
    }

    public List<DisciplinaDto> buscarTodasDisciplinas() {
        return disciplinaRepository.findAll()
                .stream()
                .map(d -> new DisciplinaDto(d.getId(), d.getNome()))
                .collect(Collectors.toList());
    }

    public Optional<Disciplina> buscarDisciplinaPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public List<DisciplinaDto> buscarTodas() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        return disciplinas.stream()
                .map(d -> new DisciplinaDto(d.getId(), d.getNome()))
                .collect(Collectors.toList());
    }
}