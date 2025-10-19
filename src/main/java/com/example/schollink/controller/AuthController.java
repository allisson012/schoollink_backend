package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.model.Admin;
import com.example.schollink.model.User;
import com.example.schollink.service.AuthService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login/professor")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> loginRequest,
            HttpSession session) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Optional<User> usuario = authService.autenticarProfessor(email, password);

        Map<String, String> response = new HashMap<>();
        if (usuario.isPresent()) {
            session.setAttribute("userId", usuario.get().getId());
            session.setAttribute("nome", usuario.get().getNome());
            response.put("message", "Login bem sucedido");
            response.put("id", String.valueOf(usuario.get().getId()));
            response.put("nome", usuario.get().getNome());
            return ResponseEntity.ok(response);
        }

        response.put("message", "Email ou senha inválidos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/login/aluno")
    public ResponseEntity<Map<String, String>> loginAluno(@RequestBody Map<String, String> loginRequest,
            HttpSession session) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Optional<User> usuario = authService.autenticarAluno(email, password);

        Map<String, String> response = new HashMap<>();
        if (usuario.isPresent()) {
            session.setAttribute("userId", usuario.get().getId());
            session.setAttribute("nome", usuario.get().getNome());
            response.put("message", "Login bem sucedido");
            response.put("id", String.valueOf(usuario.get().getId()));
            response.put("nome", usuario.get().getNome());
            return ResponseEntity.ok(response);
        }

        response.put("message", "Email ou senha inválidos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody Map<String, String> loginRequest,
            HttpSession session) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Optional<Admin> adminOpt = authService.autenticarAdmin(email, password);

        Map<String, String> response = new HashMap<>();
        if (adminOpt.isPresent()) {
            session.setAttribute("userId", adminOpt.get().getId_admin());
            response.put("message", "Login bem sucedido");
            response.put("id", String.valueOf(adminOpt.get().getId_admin()));
            return ResponseEntity.ok(response);
        }
        response.put("message", "Email ou senha inválidos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            return ResponseEntity.ok(Map.of("loggedIn", true, "userId", userId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("loggedIn", false));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logout realizado com sucesso"));
    }

}
