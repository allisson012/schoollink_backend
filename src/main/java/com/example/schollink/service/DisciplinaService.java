package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Professor;
import com.example.schollink.model.Turma;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.repository.DisciplinaRepository;
import com.example.schollink.repository.ProfessorRepository;
import com.example.schollink.repository.TurmaDisciplinaRepository;
import com.example.schollink.repository.TurmaRepository;

import jakarta.transaction.Transactional;

import com.example.schollink.Dto.DisciplinaDto;
import com.example.schollink.Dto.DisciplinaProfessorDto;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;
    @Autowired
    private TurmaRepository turmaRepository;

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

    public List<DisciplinaProfessorDto> buscarDisciplinasProfessores(Long idTurma) {
        List<DisciplinaProfessorDto> disciplinasProfessoresDtos = new ArrayList();
        Optional<Turma> turmaOpt = turmaRepository.findById(idTurma);
        if (turmaOpt.isPresent()) {
            Turma turma = turmaOpt.get();
            List<TurmaDisciplina> turmasDisciplinas = turmaDisciplinaRepository.findByTurma(turma);
            for (TurmaDisciplina turmaDisciplina : turmasDisciplinas) {
                DisciplinaProfessorDto disciplinaProfessorDto = new DisciplinaProfessorDto();
                disciplinaProfessorDto.setIdDisciplina(turmaDisciplina.getDisciplina().getId());
                disciplinaProfessorDto.setNomeDisciplina(turmaDisciplina.getDisciplina().getNome());
                disciplinaProfessorDto.setIdProfessor(turmaDisciplina.getProfessor().getId());
                disciplinaProfessorDto.setNomeProfessor(turmaDisciplina.getProfessor().getUser().getNome());
                disciplinasProfessoresDtos.add(disciplinaProfessorDto);
            }
        }

        if (!disciplinasProfessoresDtos.isEmpty()) {
            return disciplinasProfessoresDtos;
        } else {
            return new ArrayList<>();
        }
    }

    public List<DisciplinaDto> buscarTodas() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        return disciplinas.stream()
                .map(d -> new DisciplinaDto(d.getId(), d.getNome()))
                .collect(Collectors.toList());
    }

    public List<DisciplinaDto> buscarDisciplinaPorProfessorETurma(Long idTurma, Long userId) {
        Professor professor = professorRepository.findByUser_Id(userId);
        if (professor == null) {
            return List.of(); 
        }

        List<TurmaDisciplina> lista = turmaDisciplinaRepository.findByTurmaIdAndProfessorId(idTurma, professor.getId());

        return lista.stream()
                .map(td -> new DisciplinaDto(
                        td.getDisciplina().getId(),
                        td.getDisciplina().getNome()
                ))
                .toList();
    }
}