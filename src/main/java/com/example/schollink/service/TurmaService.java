package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.DisciplinaProfessorDto;
import com.example.schollink.Dto.TurmaDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Professor;
import com.example.schollink.model.Turma;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.DisciplinaRepository;
import com.example.schollink.repository.ProfessorRepository;
import com.example.schollink.repository.TurmaDisciplinaRepository;
import com.example.schollink.repository.TurmaRepository;

import jakarta.transaction.Transactional;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;
    
    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public Turma cadastrarTurma(Turma turma, List<Integer> idAlunos, List<DisciplinaProfessorDto> disciplinas) {
        List<Aluno> alunos = new ArrayList<Aluno>();
        if (idAlunos != null && !idAlunos.isEmpty()) {
            List<Long> idAlunosLong = idAlunos.stream()
                    .map(Integer::longValue)
                    .toList();
            alunos = alunoRepository.findAllById(idAlunosLong);
            turma.setAlunos(alunos);
        }

        List<TurmaDisciplina> turmaDisciplinas = new ArrayList<>();
        if (disciplinas != null && !disciplinas.isEmpty()) {
            for (DisciplinaProfessorDto dp : disciplinas) {
                Disciplina disciplina = disciplinaRepository.findById(dp.getIdDisciplina())
                        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

                Professor professor = professorRepository.findById(dp.getIdProfessor())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                TurmaDisciplina td = new TurmaDisciplina();
                td.setTurma(turma);
                td.setDisciplina(disciplina);
                td.setProfessor(professor);

                turmaDisciplinas.add(td);
            }
        }

        turma.setTurmaDisciplinas(turmaDisciplinas);
        Turma turmaSalva = turmaRepository.save(turma);
        for (Aluno alunosSalvos : alunos) {
            alunosSalvos.setTurma(turmaSalva);
            alunoRepository.save(alunosSalvos);
        }
        return turmaSalva;
    }

    public List<TurmaDto> listarTurmas() {
        List<Turma> turmas = turmaRepository.findAll();
        List<TurmaDto> dtos = new ArrayList<>();
        for (Turma turma : turmas) {
            TurmaDto dto = new TurmaDto();
            dto.setId(turma.getId());
            dto.setNome(turma.getNome());
            dto.setAnoLetivo(turma.getAnoLetivo());
            dtos.add(dto);
        }

        if (!dtos.isEmpty()) {
            return dtos;
        } else {
            return new ArrayList<>();
        }
    }

    public Turma buscarTurma(Long id) {
        return turmaRepository.findById(id).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }

    public void deletarTurma(Long id) {
        turmaRepository.deleteById(id);
    }

    public Turma adicionarAluno(Long turmaId, Long alunoId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow();
        Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        turma.getAlunos().add(aluno);
        return turmaRepository.save(turma);
    }

    public Turma removerAluno(Long turmaId, Long alunoId) {
        Turma turma = buscarTurma(turmaId);
        turma.getAlunos().removeIf(aluno -> aluno.getIdAluno().equals(alunoId));
        return turmaRepository.save(turma);
    }

    public Turma adicionarDisciplina(Long turmaId, Long disciplinaId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow();
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        // turma.getDisciplinas().add(disciplina);
        return turmaRepository.save(turma);
    }

    public Turma removerDisciplina(Long turmaId, Long disciplinaId) {
        Turma turma = buscarTurma(turmaId);
        // turma.getDisciplinas().removeIf(disciplina ->
        // disciplina.getId().equals(disciplinaId));
        return turmaRepository.save(turma);
    }

    @Transactional
    public void editarTurma(Long id, TurmaDto turmaDto) {
        Turma turma = turmaRepository.findById(id).orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        turma.setNome(turmaDto.getNome());
        turma.setAnoLetivo(turmaDto.getAnoLetivo());
        turma.setAnoEscolar(turmaDto.getAnoEscolar());
        List<Aluno> alunosAntigos = turma.getAlunos();
        for (Aluno antigo : alunosAntigos) {
            antigo.setTurma(null);
            alunoRepository.save(antigo);
        }

        List<Aluno> novosAlunos = new ArrayList<Aluno>();
        if (turmaDto.getIdAlunos() != null && !turmaDto.getIdAlunos().isEmpty()) {
            List<Long> idAlunosLong = turmaDto.getIdAlunos().stream()
                    .map(Integer::longValue)
                    .toList();
            novosAlunos = alunoRepository.findAllById(idAlunosLong);
            turma.setAlunos(novosAlunos);
        } else {
            turma.setAlunos(new ArrayList<>());
        }

        turma.getTurmaDisciplinas().clear();

        if (turmaDto.getDisciplinas() != null && !turmaDto.getDisciplinas().isEmpty()) {
            for (DisciplinaProfessorDto dp : turmaDto.getDisciplinas()) {
                Disciplina disciplina = disciplinaRepository.findById(dp.getIdDisciplina())
                        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

                Professor professor = professorRepository.findById(dp.getIdProfessor())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                TurmaDisciplina td = new TurmaDisciplina();
                td.setTurma(turma);
                td.setDisciplina(disciplina);
                td.setProfessor(professor);

                turma.getTurmaDisciplinas().add(td);
            }
        }

        turmaRepository.save(turma);

        for (Aluno novo : novosAlunos) {
            novo.setTurma(turma);
            alunoRepository.save(novo);
        }
    }

    public List<TurmaDto> buscarTodas() {
        List<Turma> turmas = turmaRepository.findAll();

        List<TurmaDto> turmasDto = turmas.stream().map(t -> {
            TurmaDto dto = new TurmaDto();
            dto.setId(t.getId());
            dto.setNome(t.getNome());
            dto.setAnoEscolar(t.getAnoEscolar());
            dto.setAnoLetivo(t.getAnoLetivo());
            List<Integer> idAlunos = new ArrayList<Integer>();
            for (Aluno aluno : t.getAlunos()) {
                if (aluno.getIdAluno() != null) {
                    idAlunos.add(aluno.getIdAluno().intValue());
                }
            }
            if (idAlunos != null && !idAlunos.isEmpty()) {
                dto.setIdAlunos(idAlunos);
            }
            List<DisciplinaProfessorDto> disciplinaProfessorDtos = new ArrayList<DisciplinaProfessorDto>();
            for (TurmaDisciplina turmaDisciplina : t.getTurmaDisciplinas()) {
                DisciplinaProfessorDto dpd = new DisciplinaProfessorDto();
                dpd.setIdDisciplina(turmaDisciplina.getDisciplina().getId());
                dpd.setIdProfessor(turmaDisciplina.getProfessor().getId());
                disciplinaProfessorDtos.add(dpd);
            }
            if (disciplinaProfessorDtos != null && !disciplinaProfessorDtos.isEmpty()) {
                dto.setDisciplinas(disciplinaProfessorDtos);
            }

            return dto;
        }).toList();

        if (turmasDto != null && !turmasDto.isEmpty()) {
            return turmasDto;
        } else {
            return new ArrayList<>();
        }
    }

    public List<TurmaDto> listarTurmasDoProfessor(Long id) {
        List<TurmaDisciplina> turmas = turmaDisciplinaRepository.findByProfessorUserId(id);
        List<TurmaDto> dtos = new ArrayList<>();
        for (TurmaDisciplina turma : turmas) {
            TurmaDto dto = new TurmaDto();
            dto.setId(turma.getTurma().getId());
            dto.setNome(turma.getTurma().getNome());
            dto.setAnoLetivo(turma.getTurma().getAnoLetivo());
            dtos.add(dto);
        }

        if (!dtos.isEmpty()) {
            return dtos;
        } else {
            return new ArrayList<>();
        }
    }
}
