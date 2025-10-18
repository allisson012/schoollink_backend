package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.FuncionarioDto;
import com.example.schollink.model.Funcionario;
import com.example.schollink.service.FuncionarioService;

@RestController()
@RequestMapping("/funcionario")
public class FuncionarioController {
@Autowired
private FuncionarioService funcionarioService;

@PostMapping("/cadastrar")
public ResponseEntity<?> cadastrarFuncionario(@RequestBody FuncionarioDto funcionarioDto){
    Funcionario funcionario = new Funcionario();
    funcionario.setNome(funcionarioDto.getNome());
    funcionario.setEmail(funcionarioDto.getEmail());
    funcionario.setGenero(funcionarioDto.getGenero());
    funcionario.setCpf(funcionarioDto.getCpf());
    funcionario.setTelefone(funcionarioDto.getTelefone());
    funcionario.setRf_id(funcionarioDto.getRf_id());
    funcionarioService.cadastrarFuncionario(funcionario);
    return null;
}
}
