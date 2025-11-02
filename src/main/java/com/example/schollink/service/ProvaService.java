package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Prova prova = new Prova();
        prova.setTurmaDisciplina(turmaDisciplina);
        prova.setPeriodo(Periodo.valueOf(dto.getBimestre()));
        prova.setTipo(TipoProva.valueOf(dto.getTipoProva()));
        provaRepository.save(prova);
        return true;
    }

    public void lancarNotas(Long turmaDisciplinaId, List<ProvaDto> notasDtos) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(turmaDisciplinaId)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));

        for (ProvaDto dto : notasDtos) {
            Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            // salvarNota(aluno, turmaDisciplina, TipoProva.P1, dto.getP1());
            // salvarNota(aluno, turmaDisciplina, TipoProva.P2, dto.getP2());
            // salvarNota(aluno, turmaDisciplina, TipoProva.AC, dto.getAc());
            // salvarNota(aluno, turmaDisciplina, TipoProva.AF, dto.getAf());
        }
    }

    public boolean salvarNota(Long idAluno, Long idProva, double nota) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(idAluno);
        Optional<Prova> provaOpt = provaRepository.findById(idProva);
        if (alunoOpt.isEmpty() || provaOpt.isEmpty()) {
            return false;
        }
        Aluno aluno = alunoOpt.get();
        Prova prova = provaOpt.get();
        ProvaAluno provaAluno = new ProvaAluno();
        boolean existe = provaAlunoRepository.existsByAlunoIdAlunoAndProvaIdProva(idAluno, idProva);
        if (existe) {
            throw new IllegalStateException("Este aluno já está vinculado a esta prova.");
        }
        provaAluno.setAluno(aluno);
        provaAluno.setProva(prova);
        provaAluno.setNota(nota);
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
}
