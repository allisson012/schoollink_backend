package com.example.schollink.Dto;

public class ProfessorDto{
    private String nome;
    private String email;
    private String senha;
    private String formacaoAcademica;
    private String registroProfissional;
    private double cargaHorariaSem;
    private String turno;
    private double salario;

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}