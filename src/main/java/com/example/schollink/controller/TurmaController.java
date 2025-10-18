package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.TurmaDto;
import com.example.schollink.model.Turma;
import com.example.schollink.service.TurmaService;

@RestController()
@RequestMapping("/turma")
public class TurmaController {
    @Autowired
    private TurmaService turmaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarTurma(@RequestBody TurmaDto turmaDto) {
        Turma turma = new Turma();
        turma.setNome(turmaDto.getNome());
        turma.setAnoLetivo(turmaDto.getAnoLetivo());
        turma.setAnoEscolar(turmaDto.getAnoEscolar());
        turmaService.cadastrarTurma(turma);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Turma Cadastrado com sucesso");
        return ResponseEntity.ok(response);
    }
}
