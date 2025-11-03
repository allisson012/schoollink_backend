package com.example.schollink.Dto;

public class HorarioFixoDto {
    private String diaDaSemana;
    private String disciplinaNome;
    private String turmaNome;
    private String horarioInicio;
    private String horarioTermino;
    public String getDiaDaSemana() {
        return diaDaSemana;
    }
    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }
    public String getDisciplinaNome() {
        return disciplinaNome;
    }
    public void setDisciplinaNome(String disciplinaNome) {
        this.disciplinaNome = disciplinaNome;
    }
    public String getTurmaNome() {
        return turmaNome;
    }
    public void setTurmaNome(String turmaNome) {
        this.turmaNome = turmaNome;
    }
    public String getHorarioInicio() {
        return horarioInicio;
    }
    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
    public String getHorarioTermino() {
        return horarioTermino;
    }
    public void setHorarioTermino(String horarioTermino) {
        this.horarioTermino = horarioTermino;
    };
    
}
