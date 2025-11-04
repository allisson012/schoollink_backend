package com.example.schollink.controller;

import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Media;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schollink.Dto.MediaDto;
import com.example.schollink.Dto.NotaDto;
import com.example.schollink.Dto.ProvaDto;
import com.example.schollink.model.Prova;
import com.example.schollink.service.ProfessorService;
import com.example.schollink.service.ProvaService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/prova")
public class ProvaController {

    @Autowired
    private ProvaService provaService;
    @Autowired
    private ProfessorService professorService;

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
    @PostMapping("/calcularMedia")
    public ResponseEntity<Map<String, Object>> calcularMedia(@RequestBody MediaDto dto) {

        Double media = provaService.calcularMedia(dto.getIdAluno(), dto.getIdTurmaDisciplina());

        Map<String, Object> response = new HashMap<>();
        response.put("alunoId", dto.getIdAluno());
        response.put("turmaDisciplinaId", dto.getIdTurmaDisciplina());
        response.put("mediaFinal", media);

        return ResponseEntity.ok(response);
    }

    // 🔹 Calcular a média de todos os alunos da turma/disciplina
    @GetMapping("/{turmaDisciplinaId}/medias")
    public ResponseEntity<List<Object[]>> listarMedias(@PathVariable Long turmaDisciplinaId) {
        return ResponseEntity.ok(provaService.listarMediasPorTurmaDisciplina(turmaDisciplinaId));
    }

    @GetMapping("/buscar/professor")
    public ResponseEntity<?> buscarProvasDoProfessor(HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        Long idProfessor = professorService.buscarIdProfessorPeloIdUser(idUser);

        if(idProfessor == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado");
        }
        return ResponseEntity.ok(provaService.buscarProvasDoProfessor(idProfessor));
    }
    
}
