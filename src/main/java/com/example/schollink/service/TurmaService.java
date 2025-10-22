package com.example.schollink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Turma;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.DisciplinaRepository;
import com.example.schollink.repository.TurmaRepository;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Turma cadastrarTurma(Turma turma, List<Integer> idAlunos, List<Integer> idDisciplinas) {
        if(idAlunos != null && !idAlunos.isEmpty()) {
            List<Long> idAlunosLong = idAlunos.stream().map(Integer::longValue).toList();
            List<Aluno> alunos = alunoRepository.findAllById(idAlunosLong);
            turma.setAlunos(alunos);
        }
        if(idDisciplinas != null && !idDisciplinas.isEmpty()) {
            List<Long> idDisciplinasLong = idDisciplinas.stream().map(Integer::longValue).toList();
            List<Disciplina> disciplinas = disciplinaRepository.findAllById(idDisciplinasLong);
            turma.setDisciplinas(disciplinas);
        }
        return turmaRepository.save(turma);
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
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        turma.getDisciplinas().add(disciplina);
        return turmaRepository.save(turma); 
    }

    public Turma removerDisciplina(Long turmaId, Long disciplinaId) {
        Turma turma = buscarTurma(turmaId);
        turma.getDisciplinas().removeIf(disciplina -> disciplina.getId().equals(disciplinaId));
        return turmaRepository.save(turma);
    }
}
