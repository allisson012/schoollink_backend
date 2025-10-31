package com.example.schollink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProva;
    private Float nota;
    @Enumerated(EnumType.STRING)
    private TipoProva tipo;
    @ManyToOne
    @JoinColumn(name = "id_turma_disciplina")
    private TurmaDisciplina turmaDisciplina;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    @ManyToOne
    @JoinColumn(name = "idAluno", referencedColumnName = "idAluno")
    private Aluno aluno;


    public Prova() {
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Long getIdProva() {
        return this.idProva;
    }

    public void setIdProva(Long idProva) {
        this.idProva = idProva;
    }

    public Float getNota() {
        return this.nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
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

}
