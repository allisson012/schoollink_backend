package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.AvisoDto;
import com.example.schollink.service.MuralService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/mural")
public class MuralController {
    @Autowired
    private MuralService muralService;

    @PostMapping("/cadastrarAviso")
    public ResponseEntity<?> cadastrarAviso(@RequestBody AvisoDto dto, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não logado");
        }
        boolean cadastrado = muralService.cadastrarAviso(dto, session);
        if (cadastrado) {
            return ResponseEntity.ok().body("Aviso cadastrado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao cadastrar aviso");
        }
    }
    
    @GetMapping("/buscarAvisos")
    public ResponseEntity<?> buscarAvisos(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não logado");
        }
        return ResponseEntity.ok(muralService.buscarAvisos(session));
    }
}
