package com.example.schollink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.HorarioFixoDto;
import com.example.schollink.model.HorarioFixo;
import com.example.schollink.repository.HorarioFixoRepository;
import com.example.schollink.service.HorarioFixoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/horarios-fixos")
public class HorarioFixoController {
    @Autowired
    private HorarioFixoService horarioFixoService;

    @PostMapping
    public ResponseEntity<HorarioFixo> criarHorarioFixo(@RequestBody HorarioFixo horarioFixo) {
        HorarioFixo salvo = horarioFixoService.cadastrarHorarioFixo(horarioFixo);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/buscarHorarioAluno")
    public ResponseEntity<List<HorarioFixoDto>> buscarHorarioAluno(HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        List<HorarioFixoDto> dtos = horarioFixoService.buscarHorarioAluno(idUser);
        return ResponseEntity.ok(dtos);
    }
}
