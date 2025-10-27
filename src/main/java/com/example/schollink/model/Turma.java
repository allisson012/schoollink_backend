package com.example.schollink.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos;
    @OneToMany(mappedBy = "turma")
    private List<Prova> provas;
    private int anoLetivo;
    @Enumerated(EnumType.STRING)
    private Anos anoEscolar;
    // uma turma tem varias disciplinas
    @OneToMany(mappedBy = "turma")
    private List<Disciplina> disciplinas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Anos getAnoEscolar() {
        return anoEscolar;
    }

    public void setAnoEscolar(Anos anoEscolar) {
        this.anoEscolar = anoEscolar;
    }

    public List<Prova> getProvas() {
        return provas;
    }

    public void setProvas(List<Prova> provas) {
        this.provas = provas;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

}
