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
import com.example.schollink.repository.TurmaRepository;

import jakarta.transaction.Transactional;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public Turma cadastrarTurma(Turma turma, List<Integer> idAlunos, List<DisciplinaProfessorDto> disciplinas) {
        List<Aluno> alunos = new ArrayList<Aluno>();
        // Adiciona os alunos, se existirem
        if (idAlunos != null && !idAlunos.isEmpty()) {
            List<Long> idAlunosLong = idAlunos.stream()
                    .map(Integer::longValue)
                    .toList();
            alunos = alunoRepository.findAllById(idAlunosLong);
            turma.setAlunos(alunos);
        }

        // Cria a lista de TurmaDisciplina
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

        // Salva a turma (e por cascade, salva TurmaDisciplina)
        Turma turmaSalva = turmaRepository.save(turma);
        for (Aluno alunosSalvos : alunos) {
            alunosSalvos.setTurma(turmaSalva);
            alunoRepository.save(alunosSalvos);
        }
        return turmaSalva;
    }

    public List<Turma> listarTurmas() {
        return turmaRepository.findAll();
    }

    public Turma buscarTurma(Long id) {
        return turmaRepository.findById(id).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }

    public Turma editarTurma(Long id, Turma turmaAtualizada) {
        Turma turmaExistente = buscarTurma(id);

        turmaExistente.setNome(turmaAtualizada.getNome());
        turmaExistente.setAnoLetivo(turmaAtualizada.getAnoLetivo());
        turmaExistente.setAnoEscolar(turmaAtualizada.getAnoEscolar());

        return turmaRepository.save(turmaExistente);
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
        }else{
            turma.setAlunos(new ArrayList<>());
        }

        turma.getTurmaDisciplinas().clear();

        if(turmaDto.getDisciplinas() != null && !turmaDto.getDisciplinas().isEmpty()){
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
}
