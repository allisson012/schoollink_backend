package com.example.schollink.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
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

import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.Dto.BuscarAulasDto;
import com.example.schollink.Dto.ProfessorDto;
import com.example.schollink.Dto.ProfessorHorarioDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Disciplina;
import com.example.schollink.model.HorarioAula;
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
        } else {
            professor.setDisciplinas(new ArrayList<>());
        }

        professor.setDisciplinas(disciplinas);

        professorService.cadastrarProfessor(user, professor, dto.getSenha());

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

    @PostMapping("/buscar/aulas")
    public ResponseEntity<?> buscarAulasDia(@RequestBody BuscarAulasDto dto) {
        Long idProfessor = dto.getIdProfessor();
        List<HorarioAula> horarioAulas = professorService.buscarAulasDia(idProfessor);
        if (!horarioAulas.isEmpty()) {
            return ResponseEntity.ok(horarioAulas);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Erro ao buscar aulas ou lista vazia"));
        }
    }

    // buscar aulas do dia

    @PostMapping("/buscar/chamada")
    public ResponseEntity<?> buscarAlunosChamada(@RequestBody ProfessorHorarioDto dto) {
        Long idProfessor = dto.getIdProfessor();
        Long idHorarioAula = dto.getIdHorarioAula();
        List<AlunoDto> alunosDtos = new ArrayList<AlunoDto>();
        alunosDtos = professorService.receberAlunosParaChamada(idProfessor, idHorarioAula);
        if (alunosDtos != null && !alunosDtos.isEmpty()) {
            return ResponseEntity.ok(alunosDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Erro ao buscar alunos ou lista vazia"));
        }
    }
}
