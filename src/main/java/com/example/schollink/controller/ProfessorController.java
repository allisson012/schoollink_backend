package com.example.schollink.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.ProfessorDto;
import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Professor;
import com.example.schollink.model.Turno;
import com.example.schollink.model.User;
import com.example.schollink.service.ProfessorService;
import com.example.schollink.service.DisciplinaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/professor")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarProfessor(@RequestBody ProfessorDto dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        Professor professor = new Professor();
        professor.setFormacaoAcademica(dto.getFormacaoAcademica());
        professor.setRegistroProfissional(dto.getRegistroProfissional());
        professor.setCargaHorariaSem(dto.getCargaHorariaSem());
        professor.setSalario(dto.getSalario());
        // professor.setTurno(Turno.valueOf(dto.getTurno().toUpperCase()));
        List<Disciplina> disciplinas = new ArrayList<>();

        if (dto.getDisciplinaIds() != null) {
            disciplinas = dto.getDisciplinaIds().stream()
                .map(id -> disciplinaService.buscarDisciplinaPorId(id)
                    .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + id)))
                .collect(Collectors.toList());
        }
        else {
            professor.setDisciplinas(new ArrayList<>());
        }

        professor.setDisciplinas(disciplinas);

        professorService.cadastrarProfessor(user, professor, dto.getSenha(), dto.getDisciplinaIds());

        return ResponseEntity.ok(Map.of("mensagem", "Professor cadastrado com sucesso"));
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarProfessor(@RequestBody Professor novo, HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        if (id == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não logado"));

        try {
            Professor atualizado = professorService.editarProfessor(novo, id);
            return ResponseEntity
                    .ok(Map.of("message", "Professor atualizado", "id", String.valueOf(atualizado.getId())));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> excluirProfessor(HttpSession session, @RequestParam Long idProfessor) {
        Long id = (Long) session.getAttribute("userId");
        if (id == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não logado"));

        try {
            professorService.deletarProfessor(idProfessor);
            return ResponseEntity.ok(Map.of("message", "Professor excluído com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
