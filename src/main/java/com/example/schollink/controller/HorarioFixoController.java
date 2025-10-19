package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.model.HorarioFixo;
import com.example.schollink.repository.HorarioFixoRepository;

@RestController
@RequestMapping("/horarios-fixos")
public class HorarioFixoController {
    @Autowired
    private HorarioFixoRepository horarioFixoRepository;

    @PostMapping
    public ResponseEntity<HorarioFixo> criarHorarioFixo(@RequestBody HorarioFixo horarioFixo) {
        HorarioFixo salvo = horarioFixoRepository.save(horarioFixo);
        return ResponseEntity.ok(salvo);
    }
}
