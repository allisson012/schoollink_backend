package com.example.schollink.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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

        turmaService.cadastrarTurma(turma, turmaDto.getIdAlunos(), turmaDto.getDisciplinas());
        
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Turma Cadastrado com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Turma>> listarTurmas() {
        return ResponseEntity.ok(turmaService.listarTurmas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarTurma(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.buscarTurma(id));
    }

    @PutMapping("/{id}/editar")
    public ResponseEntity<Turma> editarTurma(@PathVariable Long id, @RequestBody Turma turmaAtualizada) {
        Turma turmaEditada = turmaService.editarTurma(id, turmaAtualizada);
        return ResponseEntity.ok(turmaEditada);
    }

    @DeleteMapping("/{id}/deletar")
    public ResponseEntity<Map<String, String>> deletarTurma(@PathVariable Long id) {
        turmaService.deletarTurma(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Turma deletada com sucesso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{turmaId}/adicionar-aluno/{alunoId}")
    public ResponseEntity<Map<String, String>> adicionarAluno(@PathVariable Long turmaId, @PathVariable Long alunoId) {
        turmaService.adicionarAluno(turmaId, alunoId);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Aluno adicionado com sucesso");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{turmaId}/remover-aluno/{alunoId}")
    public ResponseEntity<Map<String, String>> removerAluno(@PathVariable Long turmaId, @PathVariable Long alunoId) {
        turmaService.removerAluno(turmaId, alunoId);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Aluno removido com sucesso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{turmaId}/adicionar-disciplina/{disciplinaId}")
    public ResponseEntity<Map<String, String>> adicionarDisciplina(@PathVariable Long turmaId, @PathVariable Long disciplinaId) {
        turmaService.adicionarDisciplina(turmaId, disciplinaId);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Disciplina adicionada com sucesso");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{turmaId}/remover-disciplina/{disciplinaId}")
    public ResponseEntity<Map<String, String>> removerDisciplina(@PathVariable Long turmaId, @PathVariable Long disciplinaId) {
        turmaService.removerDisciplina(turmaId, disciplinaId);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Disciplina removida com sucesso");
        return ResponseEntity.ok(response);
    }
}
