package com.example.schollink.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.HorarioFixo;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.repository.HorarioAulaRepository;
import com.example.schollink.repository.HorarioFixoRepository;
import com.example.schollink.repository.TurmaDisciplinaRepository;

@Service
public class GeradorDeAulasService {

    @Autowired
    private HorarioFixoRepository horarioFixoRepository;

    @Autowired
    private HorarioAulaRepository horarioAulaRepository;

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Scheduled(cron = "0 0 21 * * SUN", zone = "America/Sao_Paulo")
    public void gerarAulasDaSemana() {
        LocalDate hoje = LocalDate.now();
        LocalDate proximaSegunda = hoje.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        List<HorarioFixo> horariosFixos = horarioFixoRepository.findAll();

        for (HorarioFixo hf : horariosFixos) {
            LocalDate dataAula = proximaSegunda.with(TemporalAdjusters.nextOrSame(hf.getDiaSemana()));

            // Verifica se já existe uma aula para essa TurmaDisciplina e data
            boolean jaExiste = horarioAulaRepository
                    .existsByTurmaDisciplina_TurmaAndTurmaDisciplina_DisciplinaAndDataAndHoraInicio(
                            hf.getTurma(), hf.getDisciplina(), dataAula, hf.getHoraInicio());

            if (!jaExiste) {
                HorarioAula aula = new HorarioAula();

                // Aqui você precisa buscar o TurmaDisciplina correspondente
                TurmaDisciplina td = turmaDisciplinaRepository
                        .findByTurmaAndDisciplinaAndProfessor(hf.getTurma(), hf.getDisciplina(), hf.getProfessor())
                        .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada!"));

                aula.setTurmaDisciplina(td);
                aula.setDiaSemana(hf.getDiaSemana());
                aula.setHoraInicio(hf.getHoraInicio());
                aula.setHoraFim(hf.getHoraFim());
                aula.setData(dataAula);

                horarioAulaRepository.save(aula);
            }
        }

        System.out.println("✅ Aulas da semana geradas com sucesso!");
    }
}
