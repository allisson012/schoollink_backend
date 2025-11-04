package com.example.schollink.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private int modo = 0;

    private String ultimoRfid = null;

    @GetMapping("/modo")
    public Map<String, Integer> getModo() {
        return Map.of("valor", this.modo);
    }

    @PostMapping("/modo")
    public void setModo(@RequestBody Map<String, Integer> body) {
        this.modo = body.get("valor");
    }

    @PostMapping("/ponto")
    public ResponseEntity<String> registrar(@RequestBody Map<String, String> body) {
        String rfid = body.get("codigoRFID");
        String tipo = rfidService.buscarPonto(rfid);
        return switch (tipo) {
            case "aluno" -> ResponseEntity.ok("Presença registrada para aluno");
            case "funcionario" -> ResponseEntity.ok("Ponto registrado para funcionário");
            default -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("RFID não encontrado");
        };
    }

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
    public ResponseEntity<Map<String, String>> cadastrarRfid(@RequestBody Map<String, String> body) {
        String codigo = body.get("codigoRFID");
        System.out.println("Rfid para cadastro = " + codigo);
        this.modo = 0;
        this.ultimoRfid = codigo; // salva último RFID lido
        return ResponseEntity.ok(Map.of("rfid", codigo));
    }

    @GetMapping("/ultimo")
    public ResponseEntity<Map<String, String>> getUltimoRfid() {
        if (ultimoRfid == null) {
            return ResponseEntity.ok(Map.of());
        }
        return ResponseEntity.ok(Map.of("rfid", ultimoRfid));
    }

}
