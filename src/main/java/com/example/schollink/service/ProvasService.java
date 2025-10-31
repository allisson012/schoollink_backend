package com.example.schollink.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.ProvasDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Prova;
import com.example.schollink.model.TipoProva;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.ProvasRepository;
import com.example.schollink.repository.TurmaDisciplinaRepository;

@Service
public class ProvasService {

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProvasRepository provaRepository;

    public void lancarNotas(Long turmaDisciplinaId, List<ProvasDto> notasDtos) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(turmaDisciplinaId)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));

        for (ProvasDto dto : notasDtos) {
            Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            salvarNota(aluno, turmaDisciplina, TipoProva.P1, dto.getP1());
            salvarNota(aluno, turmaDisciplina, TipoProva.P2, dto.getP2());
            salvarNota(aluno, turmaDisciplina, TipoProva.AC, dto.getAc());
            salvarNota(aluno, turmaDisciplina, TipoProva.AF, dto.getAf());
        }
    }

    private void salvarNota(Aluno aluno, TurmaDisciplina turmaDisciplina, TipoProva tipo, Float nota) {
        if (nota == null) return;

        Optional<Prova> provaExistente = provaRepository.findByAlunoAndTurmaDisciplinaAndTipo(aluno, turmaDisciplina, tipo);
        Prova prova = provaExistente.orElse(new Prova());

        prova.setAluno(aluno);
        prova.setTurmaDisciplina(turmaDisciplina);
        prova.setTipo(tipo);
        prova.setNota(nota);

        provaRepository.save(prova);
    }

    public List<Prova> listarNotasPorTurmaDisciplina(Long turmaDisciplinaId) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(turmaDisciplinaId)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));
        return provaRepository.findByTurmaDisciplina(turmaDisciplina);
    }

    public Double calcularMedia(Long alunoId, Long turmaDisciplinaId) {
    List<Prova> provas = provaRepository.findByAlunoIdAlunoAndTurmaDisciplinaId(alunoId, turmaDisciplinaId);
        if (provas.isEmpty()) return 0.0;

        double soma = provas.stream()
                .filter(p -> p.getNota() != null)
                .mapToDouble(Prova::getNota)
                .sum();

        return soma / provas.size();
    }

    public List<Object[]> listarMediasPorTurmaDisciplina(Long turmaDisciplinaId) {
        return provaRepository.findMediasPorTurmaDisciplina(turmaDisciplinaId);
    }
}
