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
    @JoinColumn(name = "id_turma_disciplina")
    private TurmaDisciplina turmaDisciplina;
    private LocalDate dataAula;
    private String conteudoMinistrado;

    private Boolean tarefa;
    private String descricaoTarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isTarefa() {
        return this.tarefa;
    }

    public Boolean getTarefa() {
        return this.tarefa;
    }

    public void setTarefa(Boolean tarefa) {
        this.tarefa = tarefa;
    }

    public String getDescricaoTarefa() {
        return this.descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }

    public TurmaDisciplina getTurmaDisciplina() {
        return turmaDisciplina;
    }

    public void setTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        this.turmaDisciplina = turmaDisciplina;
    }

}
