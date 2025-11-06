package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schollink.Dto.HistoricoAlunoDto;
import com.example.schollink.service.HistoricoAlunoService;

@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "*")
public class HistoricoAlunoController {

    @Autowired
    private HistoricoAlunoService historicoAlunoService;

    @GetMapping("/teste")
public String teste() {
    return "Controller do histórico funcionando!";
}


    @GetMapping("/{idAluno}/historico")
    public ResponseEntity<HistoricoAlunoDto> gerarHistorico(@PathVariable Long idAluno) {
        HistoricoAlunoDto historico = historicoAlunoService.gerarHistorico(idAluno);
        return ResponseEntity.ok(historico);
    }
}
