package com.example.schollink.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schollink.Dto.ProvasDto;
import com.example.schollink.model.Prova;
import com.example.schollink.service.ProvasService;

@RestController
@RequestMapping("/turma-disciplina")
public class ProvasController {

    @Autowired
    private ProvasService provasService;

    // 🔹 Lançar ou atualizar notas para todos os alunos
    @PostMapping("/{turmaDisciplinaId}/lancar-notas")
    public ResponseEntity<Map<String, String>> lancarNotas(
            @PathVariable Long turmaDisciplinaId,
            @RequestBody List<ProvasDto> notasDtos) {

        provasService.lancarNotas(turmaDisciplinaId, notasDtos);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Notas lançadas com sucesso");
        return ResponseEntity.ok(response);
    }

    // 🔹 Listar todas as provas lançadas de uma turma/disciplina
    @GetMapping("/{turmaDisciplinaId}/notas")
    public ResponseEntity<List<Prova>> listarNotas(@PathVariable Long turmaDisciplinaId) {
        return ResponseEntity.ok(provasService.listarNotasPorTurmaDisciplina(turmaDisciplinaId));
    }

    // 🔹 Calcular a média de um aluno específico
    @GetMapping("/{turmaDisciplinaId}/aluno/{alunoId}/media")
    public ResponseEntity<Map<String, Object>> calcularMedia(
            @PathVariable Long turmaDisciplinaId,
            @PathVariable Long alunoId) {

        Double media = provasService.calcularMedia(alunoId, turmaDisciplinaId);

        Map<String, Object> response = new HashMap<>();
        response.put("alunoId", alunoId);
        response.put("turmaDisciplinaId", turmaDisciplinaId);
        response.put("mediaFinal", media);

        return ResponseEntity.ok(response);
    }

    // 🔹 Calcular a média de todos os alunos da turma/disciplina
    @GetMapping("/{turmaDisciplinaId}/medias")
    public ResponseEntity<List<Object[]>> listarMedias(@PathVariable Long turmaDisciplinaId) {
        return ResponseEntity.ok(provasService.listarMediasPorTurmaDisciplina(turmaDisciplinaId));
    }
}
