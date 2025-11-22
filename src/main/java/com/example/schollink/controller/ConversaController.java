package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.MensagemDto;
import com.example.schollink.model.UserRole;
import com.example.schollink.service.ConversaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/conversa")
public class ConversaController {
    @Autowired
    private ConversaService conversaService;

    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensagem(@RequestBody MensagemDto dto, HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        if (idUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario não logado");
        }
        UserRole role = (UserRole) session.getAttribute("UserRole");
        boolean enviado = conversaService.enviarMensagem(role, dto);
        if (enviado) {
            return ResponseEntity.ok().body("Mensagem enviada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao enviar mensagem");
        }
    }
}
