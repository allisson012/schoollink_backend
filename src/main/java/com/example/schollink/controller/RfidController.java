package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    private RfidService rfidService;

    @PostMapping("/ponto")
    public ResponseEntity<String> registrarPonto(@RequestBody RfidDto rfidDto) {
        rfidService.registrarPonto(rfidDto.getRfidCodigo());
        return ResponseEntity.ok("Ponto registrado: " + rfidDto.getRfidCodigo());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarRfid(@RequestBody RfidDto rfidDto) {

        return ResponseEntity.ok("RFID cadastrado: " + rfidDto.getRfidCodigo());
    }

}
