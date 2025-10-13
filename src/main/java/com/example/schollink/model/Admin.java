package com.example.schollink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_admin;
    private String email;
    private byte[] hash;
    private byte[] salt;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;    

    public Admin(Long id_admin, String email, byte[] hash, byte[] salt, UserRole userRole) {
        this.email = email;
        this.hash = hash;
        this.salt = salt;
        this.userRole = userRole;
    }

    public Admin() {
    }

    public Long getId_admin() {
        return this.id_admin;
    }

    public void setId_admin(Long id_admin) {
        this.id_admin = id_admin;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getHash() {
        return this.hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public UserRole getUserRole() {
        return this.userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


}
