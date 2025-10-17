package com.example.schollink.Dto;

import com.example.schollink.model.UserRole;

public class AdminDto {
    private String email;
    private String senha;
    private UserRole userRole;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }  
      
}
