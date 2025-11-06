package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.DisciplinaNotasDto;
import com.example.schollink.Dto.HistoricoAlunoDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Periodo;
import com.example.schollink.model.Prova;
import com.example.schollink.model.ProvaAluno;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.ProvaAlunoRepository;

@Service
public class HistoricoAlunoService {
    @Autowired
    private ProvaAlunoRepository provaAlunoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public HistoricoAlunoDto gerarHistorico(Long idAluno){
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<ProvaAluno> provasAluno = provaAlunoRepository.findByAlunoIdAluno(idAluno);

        Map<String, DisciplinaNotasDto> disciplinasMap = new HashMap<>();

        for(ProvaAluno pa : provasAluno){
            Prova prova = pa.getProva();
            String nomeDisciplina = prova.getTurmaDisciplina().getDisciplina().getNome();
            Periodo periodo = prova.getPeriodo(); // (PRIMEIRO, SEGUNDO, TERCEIRO, QUARTO)

            DisciplinaNotasDto disciplinaDto = disciplinasMap.computeIfAbsent(nomeDisciplina, k -> new DisciplinaNotasDto());
            disciplinaDto.setNomeDisciplina(nomeDisciplina);

            Map<String, Double> notasBimestre = switch (periodo) {
                case PRIMEIRO_BIMESTRE -> disciplinaDto.getPrimeiroBimestre();
                case SEGUNDO_BIMESTRE -> disciplinaDto.getSegundoBimestre();
                case TERCEIRO_BIMESTRE -> disciplinaDto.getTerceiroBimestre();
                case QUARTO_BIMESTRE -> disciplinaDto.getQuartoBimestre();
                default -> new HashMap<>();
            };

            if (notasBimestre == null)
                notasBimestre = new HashMap<>();

            
            notasBimestre.put(prova.getTipo().name(), pa.getNota());

            // Atualiza o bimestre no DTO
            switch (periodo) {
                case PRIMEIRO_BIMESTRE -> disciplinaDto.setPrimeiroBimestre(notasBimestre);
                case SEGUNDO_BIMESTRE -> disciplinaDto.setSegundoBimestre(notasBimestre);
                case TERCEIRO_BIMESTRE -> disciplinaDto.setTerceiroBimestre(notasBimestre);
                case QUARTO_BIMESTRE -> disciplinaDto.setQuartoBimestre(notasBimestre);
            }

            disciplinasMap.put(nomeDisciplina, disciplinaDto);
        }

        disciplinasMap.values().forEach(this::calcularMedias);

        HistoricoAlunoDto historico = new HistoricoAlunoDto();
        historico.setNomeAluno(aluno.getUser().getNome());
        historico.setMatricula(aluno.getMatricula());
        historico.setTurma(aluno.getTurma().getNome());
        historico.setDisciplinas(new ArrayList<>(disciplinasMap.values()));

        return historico;
    }

    private void calcularMedias(DisciplinaNotasDto dto) {
        dto.setMediaPrimeiroBi(calcularMedia(dto.getPrimeiroBimestre()));
        dto.setMediaSegundoBi(calcularMedia(dto.getSegundoBimestre()));
        dto.setMediaTerceiroBi(calcularMedia(dto.getTerceiroBimestre()));
        dto.setMediaQuartoBi(calcularMedia(dto.getQuartoBimestre()));

        // Média final = média das médias dos bimestres não nulos
        Double mediaFinal = Stream.of(
                dto.getMediaPrimeiroBi(),
                dto.getMediaSegundoBi(),
                dto.getMediaTerceiroBi(),
                dto.getMediaQuartoBi())
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        dto.setMediaFinal(mediaFinal);
    }

    private Double calcularMedia(Map<String, Double> notas) {
        if (notas == null || notas.isEmpty())
            return null;
        double soma = notas.values().stream().mapToDouble(Double::doubleValue).sum();
        return soma / notas.size();
    }
}
