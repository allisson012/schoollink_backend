package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.User;
import com.example.schollink.service.AlunoService;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;
    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String,String>> CadastrarAluno(@RequestBody AlunoDto alunoDto){
       User user = new User();
       user.setNome(alunoDto.getNome());
       user.setEmail(alunoDto.getEmail());
       Aluno aluno = new Aluno();
       aluno.setMatricula(alunoDto.getMatricula());
       alunoService.cadastrarAluno(user, aluno , alunoDto.getSenha());
       Map<String , String > response = new HashMap<>();
       response.put("mensagem" , "Aluno Cadastrado com sucesso");
       return ResponseEntity.ok(response);
    }
}
