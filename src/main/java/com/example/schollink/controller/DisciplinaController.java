package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.DisciplinaDto;
import com.example.schollink.model.Disciplina;
import com.example.schollink.service.DisciplinaService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController()
@RequestMapping("/disciplina")
public class DisciplinaController {
    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarDisciplina(@RequestBody DisciplinaDto disciplinaDto) {
        if (disciplinaDto.getNome() == null || disciplinaDto.getIdProfessor() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Nome e ID do professor são obrigatórios"));
        }
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(disciplinaDto.getNome());
        boolean sucesso = disciplinaService.cadastrarDisciplina(disciplina, disciplinaDto.getIdProfessor());
        if (sucesso) {
            return ResponseEntity.ok(Map.of("mensagem", "Disciplina cadastrada com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensagem", "Professor não encontrado, não foi possível cadastrar a disciplina"));
        }
    }
}
