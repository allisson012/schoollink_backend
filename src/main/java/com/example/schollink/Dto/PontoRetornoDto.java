package com.example.schollink.Dto;

import java.time.LocalDate;

public class PontoRetornoDto {
    private String diaDaSemana;
    private String horaEntrada;
    private String horaSaida;
    private LocalDate data;
    private boolean existe;
    public String getDiaDaSemana() {
        return diaDaSemana;
    }
    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public String getHoraSaida() {
        return horaSaida;
    }
    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public boolean isExiste() {
        return existe;
    }
    public void setExiste(boolean existe) {
        this.existe = existe;
    }
    
}
