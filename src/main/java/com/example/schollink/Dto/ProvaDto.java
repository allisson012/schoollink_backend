package com.example.schollink.Dto;

public class ProvaDto {
    private Long alunoId;
    private Long idTurmaDisciplina;
    private double nota;
    private String nome;
    private String bimestre;
    private String tipoProva;
    
    private Float p1;
    private Float p2;
    private Float ac;
    private Float af;

    public Long getAlunoId() {
        return alunoId;
    }
    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }
    public Float getP1() {
        return p1;
    }
    public void setP1(Float p1) {
        this.p1 = p1;
    }
    public Float getP2() {
        return p2;
    }
    public void setP2(Float p2) {
        this.p2 = p2;
    }
    public Float getAc() {
        return ac;
    }
    public void setAc(Float ac) {
        this.ac = ac;
    }
    public Float getAf() {
        return af;
    }
    public void setAf(Float af) {
        this.af = af;
    }
    public Long getIdTurmaDisciplina() {
        return idTurmaDisciplina;
    }
    public void setIdTurmaDisciplina(Long idTurmaDisciplina) {
        this.idTurmaDisciplina = idTurmaDisciplina;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getBimestre() {
        return bimestre;
    }
    public void setBimestre(String bimestre) {
        this.bimestre = bimestre;
    }
    public String getTipoProva() {
        return tipoProva;
    }
    public void setTipoProva(String tipoProva) {
        this.tipoProva = tipoProva;
    }
    
}
