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

    @OneToMany(mappedBy = "disciplina")
    private List<TurmaDisciplina> turmaDisciplinas;

    // @OneToMany(mappedBy = "disciplina")
    // private List<Prova> provas = new ArrayList<>();

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

    public List<TurmaDisciplina> getTurmaDisciplinas() {
        return turmaDisciplinas;
    }

    public void setTurmaDisciplinas(List<TurmaDisciplina> turmaDisciplinas) {
        this.turmaDisciplinas = turmaDisciplinas;
    }

}
