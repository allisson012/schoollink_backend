package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schollink.Dto.AlunoRetornoProvaDto;
import com.example.schollink.Dto.NotaDto;
import com.example.schollink.Dto.ProvaDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Periodo;
import com.example.schollink.model.Prova;
import com.example.schollink.model.ProvaAluno;
import com.example.schollink.model.TipoProva;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.ProvaAlunoRepository;
import com.example.schollink.repository.ProvaRepository;
import com.example.schollink.repository.TurmaDisciplinaRepository;

import jakarta.transaction.Transactional;

@Service
public class ProvaService {

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private ProvaAlunoRepository provaAlunoRepository;

    public boolean cadastrarProva(ProvaDto dto) {
        Optional<TurmaDisciplina> turmaDisciplinaOpt = turmaDisciplinaRepository.findById(dto.getIdTurmaDisciplina());
        if (turmaDisciplinaOpt.isEmpty()) {
            return false;
        }
        TurmaDisciplina turmaDisciplina = turmaDisciplinaOpt.get();
        List<Aluno> alunos = turmaDisciplina.getTurma().getAlunos();
        Prova prova = new Prova();
        prova.setTurmaDisciplina(turmaDisciplina);
        prova.setNome(dto.getNome());
        prova.setPeriodo(Periodo.valueOf(dto.getBimestre()));
        prova.setTipo(TipoProva.valueOf(dto.getTipoProva()));
        provaRepository.save(prova);
        if (!alunos.isEmpty()) {
            for (Aluno aluno : alunos) {
                ProvaAluno provaAluno = new ProvaAluno();
                provaAluno.setAluno(aluno);
                provaAluno.setProva(prova);
                provaAluno.setNota(0);
                provaAlunoRepository.save(provaAluno);
            }
        }
        return true;
    }

    @Transactional
    public boolean lancarNotas(List<NotaDto> notaDtos) {
        boolean sucesso = true;

        for (NotaDto nota : notaDtos) {
            Long idAluno = nota.getIdAluno();
            Long idProva = nota.getIdProva();
            double notaAluno = nota.getNota();

            boolean resultado = salvarNota(idAluno, idProva, notaAluno);
            if (!resultado) {
                sucesso = false;
            }
        }

        return sucesso;
    }

    public boolean salvarNota(Long idAluno, Long idProva, double nota) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(idAluno);
        Optional<Prova> provaOpt = provaRepository.findById(idProva);

        if (alunoOpt.isEmpty() || provaOpt.isEmpty()) {
            return false;
        }

        Aluno aluno = alunoOpt.get();
        Prova prova = provaOpt.get();

        Optional<ProvaAluno> provaAlunoOpt = provaAlunoRepository.findByAlunoIdAlunoAndProvaIdProva(idAluno, idProva);

        ProvaAluno provaAluno;
        if (provaAlunoOpt.isPresent()) {
            provaAluno = provaAlunoOpt.get();
            provaAluno.setNota(nota);
        } else {
            provaAluno = new ProvaAluno();
            provaAluno.setAluno(aluno);
            provaAluno.setProva(prova);
            provaAluno.setNota(nota);
        }

        provaAlunoRepository.save(provaAluno);
        return true;
    }

    public List<Prova> listarNotasPorTurmaDisciplina(Long turmaDisciplinaId) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(turmaDisciplinaId)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));
        return provaRepository.findByTurmaDisciplina(turmaDisciplina);
    }

    public Double calcularMedia(Long idAluno, Long turmaDisciplinaId) {
        Optional<TurmaDisciplina> turmaDisciplinaOpt = turmaDisciplinaRepository.findById(turmaDisciplinaId);
        Optional<Aluno> alunoOpt = alunoRepository.findById(idAluno);
        if (turmaDisciplinaOpt.isEmpty() || alunoOpt.isEmpty()) {
            return null;
        }
        TurmaDisciplina turmaDisciplina = turmaDisciplinaOpt.get();
        Aluno aluno = alunoOpt.get();
        List<Prova> provas = provaRepository.findByTurmaDisciplina(turmaDisciplina);
        List<ProvaAluno> provasAluno = new ArrayList<>();
        for (Prova prova : provas) {
            List<ProvaAluno> pa = provaAlunoRepository.findByProvaAndAluno(prova, aluno);
            provasAluno.addAll(pa);
        }
        if (provasAluno.isEmpty()) {
            return 0.0;
        }

        double soma = provasAluno.stream()
                .mapToDouble(ProvaAluno::getNota)
                .sum();
        return soma / provas.size();
    }

    public List<Object[]> listarMediasPorTurmaDisciplina(Long turmaDisciplinaId) {
        // return provaRepository.findMediasPorTurmaDisciplina(turmaDisciplinaId);
        return null;
    }

    public List<ProvaDto> buscarProvasDoProfessor(Long professorId) {
        List<Prova> provas = provaRepository.findByTurmaDisciplinaProfessorId(professorId);

        return provas.stream()
                .map(prova -> {
                    ProvaDto dto = new ProvaDto();
                    dto.setIdProva(prova.getIdProva());
                    dto.setNome(prova.getNome());
                    dto.setTipoProva(prova.getTipo().name());
                    dto.setBimestre(prova.getPeriodo().name());
                    dto.setNomeDisciplina(prova.getTurmaDisciplina().getDisciplina().getNome());
                    dto.setNomeTurma(prova.getTurmaDisciplina().getTurma().getNome());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<AlunoRetornoProvaDto> buscarAlunosProva(Long idProva) {
        Optional<Prova> provaOpt = provaRepository.findById(idProva);
        if (provaOpt.isEmpty()) {
            return new ArrayList<>();
        }
        Prova prova = provaOpt.get();
        List<ProvaAluno> provaAlunos = provaAlunoRepository.findByProva(prova);
        List<AlunoRetornoProvaDto> dtosRetorno = new ArrayList<>();
        for (ProvaAluno provaAluno : provaAlunos) {
            AlunoRetornoProvaDto alunoRetornoProvaDto = new AlunoRetornoProvaDto();
            alunoRetornoProvaDto.setIdAluno(provaAluno.getAluno().getIdAluno());
            alunoRetornoProvaDto.setNome(provaAluno.getAluno().getUser().getNome());
            alunoRetornoProvaDto.setMatricula(provaAluno.getAluno().getMatricula());
            alunoRetornoProvaDto.setNota(provaAluno.getNota());
            dtosRetorno.add(alunoRetornoProvaDto);
        }
        return dtosRetorno;
    }
}
