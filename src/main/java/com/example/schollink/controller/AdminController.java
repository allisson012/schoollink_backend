package com.example.schollink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.AdminDto;
import com.example.schollink.model.Admin;
import com.example.schollink.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarAdmin(@RequestBody AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setEmail(adminDto.getEmail());
        String senha = adminDto.getSenha();
        adminService.cadastrarAdmin(admin, senha);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Administrador Cadastrado com sucesso");
        return ResponseEntity.ok(response);
    }

}
