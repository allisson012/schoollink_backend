package com.example.schollink.Dto;

public class HistoricoAulaDto {
    private String dataAula;
    private String conteudoMinistrado;
    private String resumoAula;
    private Boolean tarefa;
    private String descricaoTarefa;
    public String getDataAula() {
        return dataAula;
    }
    public void setDataAula(String dataAula) {
        this.dataAula = dataAula;
    }
    public String getConteudoMinistrado() {
        return conteudoMinistrado;
    }
    public void setConteudoMinistrado(String conteudoMinistrado) {
        this.conteudoMinistrado = conteudoMinistrado;
    }
    public String getResumoAula() {
        return resumoAula;
    }
    public void setResumoAula(String resumoAula) {
        this.resumoAula = resumoAula;
    }
    public Boolean getTarefa() {
        return tarefa;
    }
    public void setTarefa(Boolean tarefa) {
        this.tarefa = tarefa;
    }
    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }
    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }
    
}
