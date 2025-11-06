package com.example.schollink.controller;

import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Media;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schollink.Dto.AlunoRetornoNotaDto;
import com.example.schollink.Dto.AlunoRetornoProvaDto;
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
    @PostMapping("/lancar-notas/turma")
    public ResponseEntity<Map<String, String>> lancarNotas(@RequestBody List<NotaDto> notaDtos) {

        boolean sucesso = provaService.lancarNotas(notaDtos);
        if (sucesso) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Notas lançadas com sucesso");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao lançar notas.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
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
    @GetMapping("/{turmaDisciplinaId}/notas")
    public ResponseEntity<List<Prova>> listarNotas(@PathVariable Long turmaDisciplinaId) {
        return ResponseEntity.ok(provaService.listarNotasPorTurmaDisciplina(turmaDisciplinaId));
    }

    @PostMapping("/calcularMedia")
    public ResponseEntity<Map<String, Object>> calcularMedia(@RequestBody MediaDto dto) {

        Double media = provaService.calcularMedia(dto.getIdAluno(), dto.getIdTurmaDisciplina());

        Map<String, Object> response = new HashMap<>();
        response.put("alunoId", dto.getIdAluno());
        response.put("turmaDisciplinaId", dto.getIdTurmaDisciplina());
        response.put("mediaFinal", media);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{turmaDisciplinaId}/medias")
    public ResponseEntity<List<Object[]>> listarMedias(@PathVariable Long turmaDisciplinaId) {
        return ResponseEntity.ok(provaService.listarMediasPorTurmaDisciplina(turmaDisciplinaId));
    }

    @GetMapping("/buscar/professor")
    public ResponseEntity<?> buscarProvasDoProfessor(HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        Long idProfessor = professorService.buscarIdProfessorPeloIdUser(idUser);

        if (idProfessor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado");
        }
        return ResponseEntity.ok(provaService.buscarProvasDoProfessor(idProfessor));
    }

    @GetMapping("/buscar/AlunoProva/{idProva}")
    public ResponseEntity<?> buscarAlunosProva(@PathVariable Long idProva) {
        List<AlunoRetornoProvaDto> dtosRetornos = provaService.buscarAlunosProva(idProva);
        if (dtosRetornos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar alunos");
        }
        return ResponseEntity.ok(dtosRetornos);
    }

    @GetMapping("/buscar/notasAluno/{idTurmaDisciplina}")
    public ResponseEntity<?> buscarNotasAluno(@PathVariable Long idTurmaDisciplina, HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        List<AlunoRetornoNotaDto> dtosRetornos = provaService.buscarNotasAluno(idUser, idTurmaDisciplina);
        if (dtosRetornos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar alunos");
        }
        return ResponseEntity.ok(dtosRetornos);
    }

}
