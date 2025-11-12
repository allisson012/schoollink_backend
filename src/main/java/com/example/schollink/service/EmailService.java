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
    private boolean hasAtSymbol;
    private String email;
    private boolean hasDotAfterAt;
    private int atPosicion;
    private String provedor;
    private boolean hasProvedor = false;
    private static final String[] ALLOWED_PROVIDERS = { "gmail.com", "outlook.com", "hotmail.com", "yahoo.com",
            "yahoo.com.br",
            "icloud.com", "fatec.sp.gov.br" };

    public boolean ValidateEmail(String email) {
        for (int i = 0; i < email.length(); i++) {

            char caracteres = email.charAt(i);
            if (caracteres == '@') {
                if (hasAtSymbol) {
                    return false;
                }
                hasAtSymbol = true;
                atPosicion = i;
            } else if (caracteres == '.' && hasAtSymbol && i > atPosicion + 1) {
                hasDotAfterAt = true;
            }
        }
        int index = atPosicion + 1;
        provedor = email.substring(index, email.length());

        if (provedor != null && Arrays.asList(ALLOWED_PROVIDERS).contains(provedor)) {
            hasProvedor = true;
        }

        if (hasAtSymbol && hasDotAfterAt && hasProvedor) {
            this.email = email;
        }

        return hasAtSymbol && hasDotAfterAt && hasProvedor;
    }

    public String getEmail() {
        return this.email;
    }

    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);
        email.setFrom("seuemail@gmail.com");
        mailSender.send(email);
    }

}
