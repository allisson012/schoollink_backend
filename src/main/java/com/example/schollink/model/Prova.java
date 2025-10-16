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
    private float nota;
    @Enumerated(EnumType.STRING)
    private TipoProva tipo;
    @ManyToOne
    @JoinColumn(name = "idTurma", referencedColumnName = "id")
    private Turma turma;
    @ManyToOne
    @JoinColumn(name = "idDisciplina", referencedColumnName = "id")
    private Disciplina disciplina;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    @ManyToOne
    @JoinColumn(name = "idAluno", referencedColumnName = "idAluno")
    private Aluno aluno;

    public Prova(Long idProva, float nota, TipoProva tipo, Turma turma, Periodo periodo) {
        this.idProva = idProva;
        this.nota = nota;
        this.tipo = tipo;
        this.turma = turma;
        this.periodo = periodo;
    }

    public Prova() {
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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

    public float getNota() {
        return this.nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public TipoProva getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoProva tipo) {
        this.tipo = tipo;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Periodo getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

}
