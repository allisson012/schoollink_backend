package com.example.schollink.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.AlterarSenhaDto;
import com.example.schollink.service.PasswordResetService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/password")
public class PasswordController {
    @Autowired
    private PasswordResetService passwordResetService;

    @PutMapping("/alterarSenha")
    public ResponseEntity<?> alterarSenha(@RequestBody AlterarSenhaDto dto, HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        dto.setUserId(idUser);
        boolean alterada = passwordResetService.alterarSenha(dto);
        if (alterada) {
            return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Senha atual incorreta"));
        }
    }

    @PutMapping("/esquecerSenha")
    public ResponseEntity<?> esquecerSenha(@RequestBody AlterarSenhaDto dto) {
        boolean valido = passwordResetService.esquecerSenha(dto.getEmail());
        if (valido) {
            return ResponseEntity.ok("Codigo enviado pelo email com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao enviar email");
        }
    }

    @PutMapping("/alterarSenhaPeloCodigo")
    public ResponseEntity<?> alterarSenhaPeloCodigo(@RequestBody AlterarSenhaDto dto) {
        boolean valido = passwordResetService.alterarSenhaPeloCodigo(dto.getEmail(), dto.getNovaSenha(), dto.getCodigo());
        if (valido) {
            return ResponseEntity.ok("Senha trocada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao trocar senha");
        }
    }
}
