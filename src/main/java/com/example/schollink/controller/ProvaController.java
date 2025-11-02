package com.example.schollink.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schollink.Dto.NotaDto;
import com.example.schollink.Dto.ProvaDto;
import com.example.schollink.model.Prova;
import com.example.schollink.service.ProvaService;

@RestController
@RequestMapping("/prova")
public class ProvaController {

    @Autowired
    private ProvaService provaService;

    // 🔹 Lançar ou atualizar notas para todos os alunos
    @PostMapping("/{turmaDisciplinaId}/lancar-notas")
    public ResponseEntity<Map<String, String>> lancarNotas(
            @PathVariable Long turmaDisciplinaId,
            @RequestBody List<ProvaDto> notasDtos) {

        provaService.lancarNotas(turmaDisciplinaId, notasDtos);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Notas lançadas com sucesso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarProva(@RequestBody ProvaDto dto) {
        boolean cadastrado = provaService.cadastrarProva(dto);
        if (cadastrado) {
            return ResponseEntity.ok("Prova cadastrado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar prova");
        }
    }

    @PostMapping("/salvarNota")
    public ResponseEntity<?> cadastrarProva(@RequestBody NotaDto dto) {
        boolean cadastrado = provaService.salvarNota(dto.getIdAluno(), dto.getIdProva(), dto.getNota());
        if (cadastrado) {
            return ResponseEntity.ok("Nota salva com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar nota");
        }
    }

    // 🔹 Listar todas as provas lançadas de uma turma/disciplina
    @GetMapping("/{turmaDisciplinaId}/notas")
    public ResponseEntity<List<Prova>> listarNotas(@PathVariable Long turmaDisciplinaId) {
        return ResponseEntity.ok(provaService.listarNotasPorTurmaDisciplina(turmaDisciplinaId));
    }

    // 🔹 Calcular a média de um aluno específico
    @GetMapping("/{turmaDisciplinaId}/aluno/{alunoId}/media")
    public ResponseEntity<Map<String, Object>> calcularMedia(
            @PathVariable Long turmaDisciplinaId,
            @PathVariable Long alunoId) {

        Double media = provaService.calcularMedia(alunoId, turmaDisciplinaId);

        Map<String, Object> response = new HashMap<>();
        response.put("alunoId", alunoId);
        response.put("turmaDisciplinaId", turmaDisciplinaId);
        response.put("mediaFinal", media);

        return ResponseEntity.ok(response);
    }

    // 🔹 Calcular a média de todos os alunos da turma/disciplina
    @GetMapping("/{turmaDisciplinaId}/medias")
    public ResponseEntity<List<Object[]>> listarMedias(@PathVariable Long turmaDisciplinaId) {
        return ResponseEntity.ok(provaService.listarMediasPorTurmaDisciplina(turmaDisciplinaId));
    }
}
