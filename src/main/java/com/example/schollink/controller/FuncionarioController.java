package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.FuncionarioDto;
import com.example.schollink.model.Endereco;
import com.example.schollink.model.Funcionario;
import com.example.schollink.model.Turno;
import com.example.schollink.service.FuncionarioService;

@RestController()
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarFuncionario(@RequestBody FuncionarioDto funcionarioDto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setRfid(funcionarioDto.getRfid());
        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setEmail(funcionarioDto.getEmail());
        funcionario.setCpf(funcionarioDto.getCpf());
        funcionario.setTelefone(funcionarioDto.getTelefone());
        funcionario.setDataNascimento(funcionarioDto.getDataNascimento());
        funcionario.setGenero(funcionarioDto.getGenero());
        funcionario.setDataContratacao(funcionarioDto.getDataContratacao());
        funcionario.setCargaHorariaSem(funcionarioDto.getCargaHorariaSemanal());
        funcionario.setTurno(Turno.valueOf(funcionarioDto.getTurno()));
        funcionario.setSalario(funcionarioDto.getSalario());
        if (funcionario.getEndereco() == null) {
            funcionario.setEndereco(new Endereco());
        }
        funcionario.getEndereco().setCep(funcionarioDto.getEnderecoDto().getCep());
        funcionario.getEndereco().setPais(funcionarioDto.getEnderecoDto().getPais());
        funcionario.getEndereco().setEstado(funcionarioDto.getEnderecoDto().getEstado());
        funcionario.getEndereco().setCidade(funcionarioDto.getEnderecoDto().getCidade());
        funcionario.getEndereco().setRua(funcionarioDto.getEnderecoDto().getRua());
        funcionario.getEndereco().setNumero(funcionarioDto.getEnderecoDto().getNumero());
        
        funcionarioService.cadastrarFuncionario(funcionario);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Funcionário Cadastrado com sucesso");
        return ResponseEntity.ok(response);
    }
}
