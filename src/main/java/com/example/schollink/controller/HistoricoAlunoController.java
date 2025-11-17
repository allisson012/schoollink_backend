package com.example.schollink.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.schollink.Dto.HistoricoAlunoDto;
import com.example.schollink.service.BoletimPdfService;
import com.example.schollink.service.HistoricoAlunoService;

@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class HistoricoAlunoController {

    @Autowired
    private HistoricoAlunoService historicoAlunoService;

    @Autowired
    private BoletimPdfService boletimPdfService;

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

    @GetMapping("/{idAluno}/historico/pdf")
    public ResponseEntity<byte[]> gerarBoletimPdf(@PathVariable Long idAluno) throws IOException {
        HistoricoAlunoDto historico = historicoAlunoService.gerarHistorico(idAluno);
        ByteArrayInputStream bis = boletimPdfService.gerarBoletimPdf(historico);

        String nomeAluno = historico.getNomeAluno().replaceAll("\\s+", "");
        String turma = historico.getTurma().replaceAll("\\s+", "");
        String nomeArquivo = "Boletim_" + capitalize(nomeAluno) + "-" + turma + ".pdf";

        byte[] pdfBytes = bis.readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    private String capitalize(String nome) {
        if (nome == null || nome.isEmpty())
            return nome;
        return Character.toUpperCase(nome.charAt(0)) + nome.substring(1);
    }
}
