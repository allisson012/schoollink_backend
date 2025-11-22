package com.example.schollink.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idRementente;
    private String tipo;
    private String mensagem;
    @ManyToOne
    private Conversa conversa;
    private Timestamp enviadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRementente() {
        return idRementente;
    }

    public void setIdRementente(Long idRementente) {
        this.idRementente = idRementente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Conversa getConversa() {
        return conversa;
    }

    public void setConversa(Conversa conversa) {
        this.conversa = conversa;
    }

    public Timestamp getEnviadoEm() {
        return enviadoEm;
    }

    public void setEnviadoEm(Timestamp enviadoEm) {
        this.enviadoEm = enviadoEm;
    }

}
