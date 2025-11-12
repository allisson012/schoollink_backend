package com.example.schollink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.service.EmailService;

@RestController
@RequestMapping("/test")
public class TestController {
  @Autowired
  private EmailService emailService;

  @GetMapping
  public void enviarEmail() {
    emailService.enviarEmail("pedrofialho000@gmail.com", "Horario do ponto", "ponto batido no dia 11/11/25 as 12:30");
  }
}
