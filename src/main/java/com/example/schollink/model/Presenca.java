package com.example.schollink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Presenca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPresenca;

    @ManyToOne
    @JoinColumn(name = "idAluno", referencedColumnName = "idAluno")
    private Aluno aluno;
    private Boolean presente = false;

    @ManyToOne
    @JoinColumn(name = "horarioAula_id")
    private HorarioAula horarioAula;

    // getters e setters
    public HorarioAula getHorarioAula() {
        return horarioAula;
    }

    public void setHorarioAula(HorarioAula horarioAula) {
        this.horarioAula = horarioAula;
    }

    public Long getIdPresenca() {
        return idPresenca;
    }

    public void setIdPresenca(Long idPresenca) {
        this.idPresenca = idPresenca;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }
}
