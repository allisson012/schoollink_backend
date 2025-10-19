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
import com.example.schollink.repository.HorarioAulaRepository;
import com.example.schollink.repository.HorarioFixoRepository;

@Service
public class GeradorDeAulasService {

    @Autowired
    private HorarioFixoRepository horarioFixoRepository;

    @Autowired
    private HorarioAulaRepository horarioAulaRepository;

    // roda todo domingo às 00:00 (você pode mudar pra manual depois)
    @Scheduled(cron = "0 0 0 * * SUN")
    public void gerarAulasDaSemana() {
        LocalDate hoje = LocalDate.now();
        LocalDate proximaSegunda = hoje.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        List<HorarioFixo> horariosFixos = horarioFixoRepository.findAll();

        for (HorarioFixo hf : horariosFixos) {
            LocalDate dataAula = proximaSegunda.with(TemporalAdjusters.nextOrSame(hf.getDiaSemana()));

            boolean jaExiste = horarioAulaRepository.existsByTurmaAndDisciplinaAndData(
                    hf.getTurma(), hf.getDisciplina(), dataAula);

            if (!jaExiste) {
                HorarioAula aula = new HorarioAula();
                aula.setTurma(hf.getTurma());
                aula.setDisciplina(hf.getDisciplina());
                aula.setDiaSemana(hf.getDiaSemana());
                // faltando salvar o dia da semana
                aula.setProfessor(hf.getProfessor());
                aula.setData(dataAula);
                aula.setHoraInicio(hf.getHoraInicio());
                aula.setHoraFim(hf.getHoraFim());
                horarioAulaRepository.save(aula);
            }
        }

        System.out.println("✅ Aulas da semana geradas com sucesso!");
    }
}
