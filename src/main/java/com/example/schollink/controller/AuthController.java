package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.model.User;
import com.example.schollink.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login/usuario")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        User usuario = authService.autenticarUsuario(email, password);

        Map<String, String> response = new HashMap<>();
        if (usuario != null) {
            response.put("message", "Login bem sucedido");
            response.put("id", String.valueOf(usuario.getId()));
            response.put("nome", usuario.getNome());
            return ResponseEntity.ok(response);
        }

        response.put("message", "Email ou senha inválidos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
