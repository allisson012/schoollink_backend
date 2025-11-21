package com.example.schollink.controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.schollink.service.FotoService;

import jakarta.servlet.http.HttpSession;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/foto")
public class FotoController {
    @Autowired
    private FotoService fotoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFoto(@RequestParam("arquivo") MultipartFile arquivo, HttpSession session)
            throws Exception {
        Long idUser = (Long) session.getAttribute("userId");
        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio");
        }

        Path pastaUploads = Paths.get("uploads");
        if (!Files.exists(pastaUploads)) {
            Files.createDirectories(pastaUploads);
        }

        String nomeArquivo = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();

        Path caminhoFinal = pastaUploads.resolve(nomeArquivo);

        Files.copy(arquivo.getInputStream(), caminhoFinal, StandardCopyOption.REPLACE_EXISTING);

        String caminhoParaBanco = "uploads/" + nomeArquivo;

        boolean salvo = fotoService.salvarFoto(idUser, caminhoParaBanco);
        if (salvo) {
            return ResponseEntity.ok("Salvo em: " + caminhoParaBanco);
        } else {
            return ResponseEntity.badRequest().body("Erro ao salvar imagem");
        }
    }
}
