package com.example.schollink.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    // uma disciplina so pode ter apenas um professor
    @ManyToOne
    @JoinColumn(name = "idProfessor")
    private Professor professor;

    // cada disciplina tem apenas uma turma
    @ManyToOne
    @JoinColumn(name = "idTurma")
    private Turma turma;

    @OneToMany(mappedBy = "disciplina")
    private List<Prova> provas = new ArrayList<>();

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

    public List<Prova> getProvas() {
        return this.provas;
    }

    public void setProvas(List<Prova> provas) {
        this.provas = provas;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}
