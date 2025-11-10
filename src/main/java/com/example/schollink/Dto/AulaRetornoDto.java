package com.example.schollink.Dto;

import java.time.LocalTime;

public class AulaRetornoDto {
    private Long idHorarioAula;
    private Long idDisciplina;
    private Long idProfessor;
    private String nomeDisciplina;
    private LocalTime horarioInicio;
    private LocalTime horarioTermino;
    private Long idTurmaDisciplina;

    public Long getIdHorarioAula() {
        return idHorarioAula;
    }

    public void setIdHorarioAula(Long idHorarioAula) {
        this.idHorarioAula = idHorarioAula;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioTermino() {
        return horarioTermino;
    }

    public void setHorarioTermino(LocalTime horarioTermino) {
        this.horarioTermino = horarioTermino;
    }

    public Long getIdTurmaDisciplina() {
        return idTurmaDisciplina;
    }

    public void setIdTurmaDisciplina(Long idTurmaDisciplina) {
        this.idTurmaDisciplina = idTurmaDisciplina;
    }

}
