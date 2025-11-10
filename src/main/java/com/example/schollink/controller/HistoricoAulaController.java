package com.example.schollink.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.HistoricoAulaDto;
import com.example.schollink.service.HistoricoAulaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/historicoAula")
public class HistoricoAulaController {
    @Autowired
    private HistoricoAulaService historicoAulaService;

    @GetMapping("/buscar/historicoAula/{idHorarioAula}")
    public ResponseEntity<?> buscarHistoricoAula(@PathVariable Long idHorarioAula, HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        if (idUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não autenticado"));
        }
        HistoricoAulaDto historicoAulaDto = historicoAulaService.buscarHistoricoAula(idHorarioAula);
        if (historicoAulaDto != null) {
            return ResponseEntity.ok(historicoAulaDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Erro ao buscar aulas ou lista vazia"));
        }
    }
}
