package com.example.schollink.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoricoAluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @Lob
    private String conteudoJson;// Armazena o histórico em formato JSON
    private LocalDateTime ultimaAtualizacao;

    public HistoricoAluno() {}

    public HistoricoAluno(Aluno aluno, String conteudoJson){
        this.aluno = aluno;
        this.conteudoJson = conteudoJson;
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getConteudoJson() {
        return conteudoJson;
    }

    public void setConteudoJson(String conteudoJson) {
        this.conteudoJson = conteudoJson;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }    
}
