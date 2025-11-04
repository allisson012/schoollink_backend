package com.example.schollink.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProva;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoProva tipo;
    @ManyToOne
    @JoinColumn(name = "id_turma_disciplina")
    private TurmaDisciplina turmaDisciplina;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    @OneToMany(mappedBy = "prova")
    private List<ProvaAluno> provaAlunos = new ArrayList<>();

    public Prova() {
    }

    public Long getIdProva() {
        return this.idProva;
    }

    public void setIdProva(Long idProva) {
        this.idProva = idProva;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoProva getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoProva tipo) {
        this.tipo = tipo;
    }

    public Periodo getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public TurmaDisciplina getTurmaDisciplina() {
        return turmaDisciplina;
    }

    public void setTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        this.turmaDisciplina = turmaDisciplina;
    }

    public List<ProvaAluno> getProvaAlunos() {
        return provaAlunos;
    }

    public void setProvaAlunos(List<ProvaAluno> provaAlunos) {
        this.provaAlunos = provaAlunos;
    }

}
