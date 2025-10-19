package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.service.GeradorDeAulasService;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private GeradorDeAulasService geradorDeAulasService;

    @PostMapping("/gerar-semana")
    public ResponseEntity<String> gerar() {
        geradorDeAulasService.gerarAulasDaSemana();
        return ResponseEntity.ok("Aulas da semana geradas!");
    }
}
