package com.example.schollink.Dto;

public class PresencasAlunoDto {
    private Long id;
    private int totalAulas;
    private int presencas;
    private String nomeDisciplina;
    public int getTotalAulas() {
        return totalAulas;
    }
    public void setTotalAulas(int totalAulas) {
        this.totalAulas = totalAulas;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getPresencas() {
        return presencas;
    }
    public void setPresencas(int presencas) {
        this.presencas = presencas;
    }
    public String getNomeDisciplina() {
        return nomeDisciplina;
    }
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    
}
