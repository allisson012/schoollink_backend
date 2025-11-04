package com.example.schollink.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.DisciplinaDto;
import com.example.schollink.Dto.DisciplinaProfessorDto;
import com.example.schollink.model.Disciplina;
import com.example.schollink.service.DisciplinaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController()
@RequestMapping("/disciplina")
public class DisciplinaController {
    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarDisciplina(@RequestBody DisciplinaDto disciplinaDto) {
        if (disciplinaDto.getNome() == null && disciplinaDto.getNome().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Nome da disciplina é obrigatório"));
        }
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(disciplinaDto.getNome());
        disciplinaService.cadastrarDisciplina(disciplina);
        return ResponseEntity.ok(Map.of("mensagem", "Disciplina cadastrada com sucesso"));
    }

    @GetMapping("/buscar-todas")
    public List<DisciplinaDto> buscarTodas() {
        return disciplinaService.buscarTodas();
    }

    @GetMapping("/buscarDisciplinas")
    public ResponseEntity<List<DisciplinaProfessorDto>> buscarDisciplinasProfessores(@RequestParam Long id) {
        return ResponseEntity.ok(disciplinaService.buscarDisciplinasProfessores(id));
    }

    // @GetMapping("/buscar-todas")
    // public ResponseEntity<List<DisciplinaDto>> buscarTodasDisciplinas() {
    // List<DisciplinaDto> disciplinas = disciplinaService.buscarTodasDisciplinas();
    // if (disciplinas.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    // }
    // return ResponseEntity.ok(disciplinas);
    // }

    @GetMapping("/buscar/turma/{idTurma}")
    public ResponseEntity<?> buscarDisciplinaDaTurmaPorProfessor(@PathVariable  Long idTurma, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado");
        }
        
        return ResponseEntity.ok(disciplinaService.buscarDisciplinaPorProfessorETurma(idTurma, userId));
    }
    

}
