package com.example.schollink.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.FuncionarioDto;
import com.example.schollink.Dto.PontoRetornoDto;
import com.example.schollink.Dto.PontoSemanaResponseDto;
import com.example.schollink.model.Funcionario;
import com.example.schollink.model.Ponto;
import com.example.schollink.repository.FuncionarioRepository;
import com.example.schollink.repository.PontoRepository;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private PontoRepository pontoRepository;

    public Funcionario cadastrarFuncionario(Funcionario funcionario){
       if(funcionario.getEmail() != null){
        return funcionarioRepository.save(funcionario);
       }
       return null;
    }
    public List<FuncionarioDto> buscarTodosFuncionarios(){
    List<Funcionario> funcionarios = funcionarioRepository.findAll();
    List<FuncionarioDto> dtos = new ArrayList<>();
    for (Funcionario funcionario : funcionarios) {
        FuncionarioDto dto = new FuncionarioDto();
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setIdFuncionario(funcionario.getIdFuncionario());
        dtos.add(dto);
    }
    return dtos;
    }

    public PontoSemanaResponseDto buscarPontos(Long idFuncionario){
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(idFuncionario);
        if(funcionarioOpt.isEmpty()){
            return null;
        }
        Funcionario funcionario = funcionarioOpt.get();
        LocalDate hoje = LocalDate.now();
        LocalDate segunda = hoje.with(DayOfWeek.MONDAY);
        LocalDate domingo = hoje.with(DayOfWeek.SUNDAY);

        List<PontoRetornoDto> listaSemana = new ArrayList<>();

        LocalDate dia = segunda;

        Duration totalSemana = Duration.ZERO;

        while (!dia.isAfter(domingo)) {

            Optional<Ponto> pontoOpt = pontoRepository.findByDataAndFuncionario(dia, funcionario);

            PontoRetornoDto dto = new PontoRetornoDto();
            dto.setData(dia);
            dto.setDiaDaSemana(dia.getDayOfWeek().name());

            if (pontoOpt.isPresent()) {
                Ponto ponto = pontoOpt.get();
                dto.setExiste(true);
                dto.setHoraEntrada(ponto.getHorarioEntrada() != null ? ponto.getHorarioEntrada().toString() : null);
                dto.setHoraSaida(ponto.getHorarioSaida() != null ? ponto.getHorarioSaida().toString() : null);
                if (ponto.getHorarioEntrada() != null && ponto.getHorarioSaida() != null) {
                    Duration duracaoDia = Duration.between(ponto.getHorarioEntrada(), ponto.getHorarioSaida());
                    totalSemana = totalSemana.plus(duracaoDia);
                }
            } else {
                dto.setExiste(false);
                dto.setHoraEntrada(null);
                dto.setHoraSaida(null);
            }

            listaSemana.add(dto);
            dia = dia.plusDays(1);
        }

        long horas = totalSemana.toHours();
        long minutos = totalSemana.toMinutes() % 60;
        long segundos = totalSemana.getSeconds() % 60;
        String totalFormatado = horas + "h " + minutos + "m " + segundos + "s";
        PontoSemanaResponseDto dtoRetorno = new PontoSemanaResponseDto();
        dtoRetorno.setPontos(listaSemana);
        dtoRetorno.setTotalHoras(totalFormatado);
        return dtoRetorno;
    }
}
