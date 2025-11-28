package com.example.schollink.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.FuncionarioDto;
import com.example.schollink.Dto.PontoRetornoDto;
import com.example.schollink.Dto.PontoSemanaResponseDto;
import com.example.schollink.model.Endereco;
import com.example.schollink.model.Funcionario;
import com.example.schollink.model.Turno;
import com.example.schollink.model.UserRole;
import com.example.schollink.service.FuncionarioService;

import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<FuncionarioDto>> buscarTodosFuncionarios(HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        UserRole userRole = (UserRole) session.getAttribute("UserRole");
        if (idUser == null || userRole != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<FuncionarioDto> dtos = funcionarioService.buscarTodosFuncionarios();
        if (dtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok().body(dtos);
        }
    }

    @GetMapping("/buscarPontos/{idFuncionario}")
    public ResponseEntity<PontoSemanaResponseDto> buscarPontos(@PathVariable Long idFuncionario, HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        UserRole userRole = (UserRole) session.getAttribute("UserRole");
        if (idUser == null || userRole != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        PontoSemanaResponseDto dtoRetorno = funcionarioService.buscarPontos(idFuncionario);
        if (dtoRetorno == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok().body(dtoRetorno);
        }
    }
}
