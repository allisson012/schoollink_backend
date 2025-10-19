package com.example.schollink.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Funcionario;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.Ponto;
import com.example.schollink.model.Presenca;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.FuncionarioRepository;
import com.example.schollink.repository.HorarioAulaRepository;
import com.example.schollink.repository.PontoRepository;
import com.example.schollink.repository.PresencaRepository;

@Service
public class RfidService {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private PresencaRepository presencaRepository;
    @Autowired
    private HorarioAulaRepository horarioAulaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private PontoRepository pontoRepository;

    public boolean registrarPonto(String rfid) {
        Optional<Aluno> alunoOpt = alunoRepository.findByRfid(rfid);
        if (alunoOpt.isEmpty()) {
            return false;
        }

        Aluno aluno = alunoOpt.get();
        LocalDate dataAtual = LocalDate.now();
        List<HorarioAula> aulasDoDia = horarioAulaRepository.findByDataAndTurma(dataAtual, aluno.getTurma());

        if (aulasDoDia.isEmpty()) {
            return false;
        }

        for (HorarioAula horarioAula : aulasDoDia) {
            boolean jaRegistrada = presencaRepository.existsByAlunoAndHorarioAula(aluno, horarioAula);
            if (!jaRegistrada) {
                Presenca presenca = new Presenca();
                presenca.setAluno(aluno);
                presenca.setPresente(true);
                presenca.setHorarioAula(horarioAula);
                presencaRepository.save(presenca);
            }
        }
        return true;
    }

    public boolean registrarPontoFuncionario(String rfid) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByRfid(rfid);
        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();
            LocalDate dataAtual = LocalDate.now();
            LocalTime horaAtual = LocalTime.now();
            boolean cadastrado = pontoRepository.existsByDataAndFuncionario(dataAtual, funcionario);
            if (cadastrado) {
                Optional<Ponto> pontoCadastradoOpt = pontoRepository.findByDataAndFuncionario(dataAtual, funcionario);
                if (pontoCadastradoOpt.isPresent()) {
                    Ponto pontoCadastrado = pontoCadastradoOpt.get();
                    pontoCadastrado.setHorarioSaida(horaAtual);
                    pontoRepository.save(pontoCadastrado);
                    return true;
                }
            }
            Ponto ponto = new Ponto();
            ponto.setData(dataAtual);
            ponto.setHorarioEntrada(horaAtual);
            ponto.setFuncionario(funcionario);
            pontoRepository.save(ponto);
            return true;
        } else {
            return false;
        }
    }

}
