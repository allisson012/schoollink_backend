package com.example.schollink.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.RfidDto;
import com.example.schollink.service.RfidService;

@RestController()
@RequestMapping("/rfid")
public class RfidController {
    @Autowired
    RfidService rfidService;

    @PostMapping("/ponto/aluno")
    public ResponseEntity<String> registrarPonto(@RequestBody RfidDto rfidDto) {
        boolean resposta = rfidService.registrarPonto(rfidDto.getRfidCodigo());
        if (resposta) {
            return ResponseEntity.ok("Ponto registrado: " + rfidDto.getRfidCodigo());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }
    }

    @PostMapping("/ponto/funcionario")
    public ResponseEntity<String> registrarPontoFuncionario(@RequestBody RfidDto rfidDto) {
        boolean resposta = rfidService.registrarPontoFuncionario(rfidDto.getRfidCodigo());
        if (resposta) {
            return ResponseEntity.ok("Ponto registrado: " + rfidDto.getRfidCodigo());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarRfid(@RequestBody RfidDto rfidDto) {

        return ResponseEntity.ok("RFID cadastrado: " + rfidDto.getRfidCodigo());
    }

}
