package com.example.schollink.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.DisciplinaNotasDto;
import com.example.schollink.Dto.HistoricoAlunoDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.HistoricoAluno;
import com.example.schollink.model.Periodo;
import com.example.schollink.model.Prova;
import com.example.schollink.model.ProvaAluno;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.HistoricoAlunoRepository;
import com.example.schollink.repository.ProvaAlunoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HistoricoAlunoService {

    @Autowired
    private ProvaAlunoRepository provaAlunoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private HistoricoAlunoRepository historicoAlunoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Double> safe(Map<String, Double> map) {
        return map != null ? map : new HashMap<>();
    }

    public HistoricoAlunoDto gerarHistorico(Long idAluno){
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<ProvaAluno> provasAluno = provaAlunoRepository.findByAlunoIdAluno(idAluno);

        Map<String, DisciplinaNotasDto> disciplinasMap = new HashMap<>();

        for(ProvaAluno pa : provasAluno){
            Prova prova = pa.getProva();
            String nomeDisciplina = prova.getTurmaDisciplina().getDisciplina().getNome();
            Periodo periodo = prova.getPeriodo();

            DisciplinaNotasDto disciplinaDto = disciplinasMap.computeIfAbsent(nomeDisciplina, k -> new DisciplinaNotasDto());
            disciplinaDto.setNomeDisciplina(nomeDisciplina);

            Map<String, Double> notasBimestre = switch (periodo) {
                case PRIMEIRO_BIMESTRE -> safe(disciplinaDto.getPrimeiroBimestre());
                case SEGUNDO_BIMESTRE -> safe(disciplinaDto.getSegundoBimestre());
                case TERCEIRO_BIMESTRE -> safe(disciplinaDto.getTerceiroBimestre());
                case QUARTO_BIMESTRE -> safe(disciplinaDto.getQuartoBimestre());
                default -> new HashMap<>();
            };

            notasBimestre.put(prova.getTipo().name(), pa.getNota());

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
        dto.setMediaPrimeiroBi(calcularMedia(safe(dto.getPrimeiroBimestre())));
        dto.setMediaSegundoBi(calcularMedia(safe(dto.getSegundoBimestre())));
        dto.setMediaTerceiroBi(calcularMedia(safe(dto.getTerceiroBimestre())));
        dto.setMediaQuartoBi(calcularMedia(safe(dto.getQuartoBimestre())));

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
        return notas.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public void atualizarHistoricoAluno(Long idAluno) {
        HistoricoAlunoDto historicoDto = gerarHistorico(idAluno);
        String json;
        try {
            json = objectMapper.writeValueAsString(historicoDto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter histórico para JSON", e);
        }

        HistoricoAluno historico = historicoAlunoRepository.findByAlunoIdAluno(idAluno)
            .orElse(new HistoricoAluno());

        historico.setAluno(alunoRepository.findById(idAluno).orElseThrow());
        historico.setConteudoJson(json);
        historico.setUltimaAtualizacao(LocalDateTime.now());
        historicoAlunoRepository.save(historico);
    }

    public Optional<HistoricoAlunoDto> getHistoricoSalvo(Long idAluno) {
        return historicoAlunoRepository.findByAlunoIdAluno(idAluno)
                .map(historico -> {
                    try {
                        return objectMapper.readValue(historico.getConteudoJson(), HistoricoAlunoDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Erro ao converter JSON do histórico", e);
                    }
                });
    }
}
