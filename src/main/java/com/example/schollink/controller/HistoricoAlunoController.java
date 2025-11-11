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

    @GetMapping("/{idAluno}/historico")
    public ResponseEntity<HistoricoAlunoDto> gerarHistorico(@PathVariable Long idAluno) {
        HistoricoAlunoDto historico = historicoAlunoService.gerarHistorico(idAluno);
        return ResponseEntity.ok(historico);
    }

    @PostMapping("/{idAluno}/historico/salvar")
    public ResponseEntity<String> salvarHistorico(@PathVariable Long idAluno) {
        try {
            historicoAlunoService.atualizarHistoricoAluno(idAluno);
            return ResponseEntity.ok("Histórico do aluno atualizado e salvo com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar histórico: " + e.getMessage());
        }
    }
    
    @GetMapping("/{idAluno}/historico/salvo")
    public ResponseEntity<HistoricoAlunoDto> buscarHistoricoSalvo(@PathVariable Long idAluno) {
        var historicoOpt = historicoAlunoService.getHistoricoSalvo(idAluno);
        return historicoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
