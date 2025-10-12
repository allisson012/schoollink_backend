package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.User;
import com.example.schollink.service.AlunoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> CadastrarAluno(@RequestBody AlunoDto alunoDto) {
        User user = new User();
        user.setNome(alunoDto.getNome());
        user.setEmail(alunoDto.getEmail());
        Aluno aluno = new Aluno();
        aluno.setMatricula(alunoDto.getMatricula());
        alunoService.cadastrarAluno(user, aluno, alunoDto.getSenha());
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Aluno Cadastrado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarAluno(@RequestBody Aluno alunoNovo, HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuario não logado"));
        }
        try {
            Aluno atualizado = alunoService.editarAluno(alunoNovo, id);
            return ResponseEntity.ok(Map.of("message", "Aluno atualizado com sucesso",
                    "id", String.valueOf(
                            atualizado.getIdAluno())));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
