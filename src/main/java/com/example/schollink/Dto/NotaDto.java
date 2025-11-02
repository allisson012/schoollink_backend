package com.example.schollink.Dto;

public class NotaDto {
    private Long idAluno;
    private Long idProva;
    private double nota;
    public Long getIdAluno() {
        return idAluno;
    }
    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }
    public Long getIdProva() {
        return idProva;
    }
    public void setIdProva(Long idProva) {
        this.idProva = idProva;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
}
