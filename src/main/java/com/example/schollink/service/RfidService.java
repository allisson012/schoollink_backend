package com.example.schollink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.Presenca;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.HorarioAulaRepository;
import com.example.schollink.repository.PresencaRepository;

@Service
public class RfidService {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private PresencaRepository presencaRepository;
    @Autowired
    private HorarioAulaRepository horarioAulaRepository;

    public boolean registrarPonto(String rfid) {
        System.out.println("Dentro do service " + rfid);
        Optional<Aluno> alunoOpt = alunoRepository.findByRfid(rfid);
        if (alunoOpt.isPresent()) {
            Aluno aluno = new Aluno();
            aluno = alunoOpt.get();
            System.out.println("aluno rfid" + aluno.getRfid());
            Presenca presenca = new Presenca();
            presenca.setAluno(aluno);
            presenca.setPresente(true);
            Optional<HorarioAula> horarioAulaOpt;
            Long id = 1L;
            horarioAulaOpt = horarioAulaRepository.findById(id);
            HorarioAula horarioAula = new HorarioAula();
            if (horarioAulaOpt.isPresent()) {
                horarioAula = horarioAulaOpt.get();
                System.out.println("horario de aula = " + horarioAula.getDiaSemana());
                presenca.setHorarioAula(horarioAula);
                aluno.getPresencas().add(presenca);
                alunoRepository.save(aluno);
                presencaRepository.save(presenca);
                return true;
            } else {
                return false;
            }
            // as aulas vou ter que pegar pelo dia e atribuir a presença para elas
            // salvar presença
        }
        return false;
    }
}
