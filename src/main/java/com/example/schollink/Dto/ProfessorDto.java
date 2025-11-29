package com.example.schollink.Dto;

import java.time.LocalDate;
import java.util.List;

public class ProfessorDto{
    private UserDto userDto;    
    private List<Long> disciplinaIds;
    private LocalDate dataContratacao; 
    private String formacaoAcademica;
    private String registroProfissional;
    private double cargaHorariaSem; 
    private String turno;
    private double salario;
    private EnderecoDto enderecoDto;
    private String rfid;
    private String caminhoFoto;

    public String getCaminhoFoto() {
        return this.caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getRfid() {
        return this.rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }


    public UserDto getUserDto() {
        return this.userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<Long> getDisciplinaIds() {
        return this.disciplinaIds;
    }

    public void setDisciplinaIds(List<Long> disciplinaIds) {
        this.disciplinaIds = disciplinaIds;
    }

    public LocalDate getDataContratacao() {
        return this.dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getFormacaoAcademica() {
        return this.formacaoAcademica;
    }

    public void setFormacaoAcademica(String formacaoAcademica) {
        this.formacaoAcademica = formacaoAcademica;
    }

    public String getRegistroProfissional() {
        return this.registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public double getCargaHorariaSem() {
        return this.cargaHorariaSem;
    }

    public void setCargaHorariaSem(double cargaHorariaSem) {
        this.cargaHorariaSem = cargaHorariaSem;
    }

    public String getTurno() {
        return this.turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public double getSalario() {
        return this.salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public EnderecoDto getEnderecoDto() {
        return this.enderecoDto;
    }

    public void setEnderecoDto(EnderecoDto enderecoDto) {
        this.enderecoDto = enderecoDto;
    }
    
}