package com.example.schollink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.schollink.model.Admin;
import com.example.schollink.model.UserRole;
import com.example.schollink.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordService passwordService;

    public void cadastrarAdmin(Admin admin, String senha) {
        byte[] salt = passwordService.gerarSalt();
        byte[] hash = passwordService.gerarHash(senha, salt);
        admin.setSalt(salt);
        admin.setHash(hash);
        admin.setUserRole(UserRole.ADMIN);
        adminRepository.save(admin);
    }
}
