package com.example.schollink.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "idFuncionario", referencedColumnName = "idFuncionario")
    private Funcionario funcionario;

    private LocalDate dataContratacao;
    private String formacaoAcademica;

    @ManyToMany
    @JoinTable(
        name = "professor_disciplina",
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinas = new ArrayList<>();

    // disciplinas que leciona
    // turmas estou pensando em colocar todas turmas que o professor ministra para o
    // acesso a elas ficar mais simples
    private String registroProfissional;
    private double cargaHorariaSem;
    @Enumerated(EnumType.STRING)
    private Turno turno;
    private double salario;

    // historico de aulas os dois teria que ter classe separadas para guardar essas
    // informações
    // horario das aulas

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getFormacaoAcademica() {
        return formacaoAcademica;
    }

    public void setFormacaoAcademica(String formacaoAcademica) {
        this.formacaoAcademica = formacaoAcademica;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public double getCargaHorariaSem() {
        return cargaHorariaSem;
    }

    public void setCargaHorariaSem(double cargaHorariaSem) {
        this.cargaHorariaSem = cargaHorariaSem;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    
}
