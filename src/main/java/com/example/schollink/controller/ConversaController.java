package com.example.schollink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.ConversaDto;
import com.example.schollink.Dto.MensagemDto;
import com.example.schollink.model.UserRole;
import com.example.schollink.service.ConversaService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

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
        System.out.println(dto.getIdRemetente());
        System.out.println(dto.getIdAluno());
        System.out.println(dto.getMensagem());
        boolean enviado = conversaService.enviarMensagem(role, dto);
        if (enviado) {
            return ResponseEntity.ok().body("Mensagem enviada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao enviar mensagem");
        }
    }

    @GetMapping("/buscarConversaAluno")
    public ResponseEntity<?> buscarConversaAluno(HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        if (idUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario não logado");
        }
        Long idConversa = conversaService.buscarConversaAluno(idUser);
        if (idConversa == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao buscar conversa");
        } else {
            return ResponseEntity.ok(idConversa);
        }
    }

    @GetMapping("/buscarTodasConversas")
    public ResponseEntity<?> buscarTodosChats(HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        UserRole role = (UserRole) session.getAttribute("UserRole");
        if (idUser == null || role != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario não logado");
        }
        List<ConversaDto> dtos = conversaService.buscarTodosChats();
        if (dtos == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao buscar conversas");
        } else {
            return ResponseEntity.ok(dtos);
        }
    }

    @GetMapping("/buscarMensagens/{idConversa}")
    public ResponseEntity<List<MensagemDto>> buscarMensagens(@PathVariable Long idConversa, HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        if (idUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<MensagemDto> dtos = conversaService.buscarMensagens(idConversa);
        if (dtos == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(dtos);
        }
    }
}
