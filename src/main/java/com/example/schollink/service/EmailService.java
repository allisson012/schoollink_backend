package com.example.schollink.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    private String email;
    private static final String[] ALLOWED_PROVIDERS = { "gmail.com", "outlook.com", "hotmail.com", "yahoo.com",
            "yahoo.com.br",
            "icloud.com", "fatec.sp.gov.br",
            "escola.com"
    };

    public boolean ValidateEmail(String email) {
        boolean hasAtSymbol = false;
        boolean hasDotAfterAt = false;
        int atPosicion = -1;

        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);

            if (c == '@') {
                if (hasAtSymbol) {
                    return false; // dois @
                }
                hasAtSymbol = true;
                atPosicion = i;
            } else if (c == '.' && hasAtSymbol && i > atPosicion + 1) {
                hasDotAfterAt = true;
            }
        }

        if (!hasAtSymbol || !hasDotAfterAt) {
            return false;
        }

        String provedor = email.substring(atPosicion + 1);

        boolean hasProvedor = Arrays.asList(ALLOWED_PROVIDERS).contains(provedor);

        return hasProvedor;
    }

    public String getEmail() {
        return this.email;
    }

    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);
        email.setFrom("allissoncastilho@gmail.com");
        mailSender.send(email);
    }

}
