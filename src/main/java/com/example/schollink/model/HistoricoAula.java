package com.example.schollink.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HistoricoAula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idProfessor")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "idTurma")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "idDisciplina")
    private Disciplina disciplina;

    private LocalDate dataAula;
    private String conteudoMinistrado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDate getDataAula() {
        return dataAula;
    }

    public void setDataAula(LocalDate dataAula) {
        this.dataAula = dataAula;
    }

    public String getConteudoMinistrado() {
        return conteudoMinistrado;
    }

    public void setConteudoMinistrado(String conteudoMinistrado) {
        this.conteudoMinistrado = conteudoMinistrado;
    }

}
