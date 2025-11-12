package com.example.schollink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.HistoricoAulaDto;
import com.example.schollink.model.HistoricoAula;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.repository.HistoricoAulaRepository;
import com.example.schollink.repository.HorarioAulaRepository;

@Service
public class HistoricoAulaService {
    @Autowired
    private HorarioAulaRepository horarioAulaRepository;
    
    @Autowired
    private HistoricoAulaRepository historicoAulaRepository;

    public HistoricoAulaDto buscarHistoricoAula(Long idHorarioAula){
        Optional<HorarioAula> horarioAulaOpt = horarioAulaRepository.findById(idHorarioAula);
        if(horarioAulaOpt.isEmpty()){
          return null;
        }
        HorarioAula horarioAula = horarioAulaOpt.get();
        HistoricoAula historicoAula = historicoAulaRepository.findByHorarioAula(horarioAula);
        if(historicoAula == null){
          return null;
        }
        HistoricoAulaDto dto = new HistoricoAulaDto();
        dto.setConteudoMinistrado(historicoAula.getConteudoMinistrado());
        dto.setDataAula(historicoAula.getDataAula().toString());
        dto.setDescricaoTarefa(historicoAula.getDescricaoTarefa());
        dto.setResumoAula(historicoAula.getResumoAula());
        dto.setTarefa(historicoAula.getTarefa());
        return dto;
      }
}
