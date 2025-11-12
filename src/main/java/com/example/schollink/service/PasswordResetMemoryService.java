package com.example.schollink.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.CodigoEmail;

@Service
public class PasswordResetMemoryService {

    private final List<CodigoEmail> codigos = new ArrayList<>();
    private static final SecureRandom random = new SecureRandom();
    @Autowired
    private EmailService emailService;

    public void gerarCodigo(String email) {
        String codigo = String.format("%06d", random.nextInt(1_000_000));
        codigos.add(new CodigoEmail(email, codigo));
        System.out.println("Código gerado para " + email + ": " + codigo);
        String destinario = email;
        String assunto = "Codigo para alteração de senha";
        emailService.enviarEmail(destinario, assunto, codigo);
    }

    public boolean validarCodigo(String email, String codigo) {
        return codigos.stream()
                .anyMatch(c -> c.getEmail().equals(email) && c.getCodigo().equals(codigo));
    }

    public void removerCodigo(String email) {
        codigos.removeIf(c -> c.getEmail().equals(email));
    }
}
