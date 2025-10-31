package com.example.schollink.Dto;

import java.util.List;

public class ChamadaRequestDto {
    private Long idHorarioAula;
    private List<AlunoDto> alunos;
    private String descricao;
    private String conteudoMinistrado;
    private String resumoAula;
    private Boolean tarefa;

    public Long getIdHorarioAula() {
        return idHorarioAula;
    }

    public void setIdHorarioAula(Long idHorarioAula) {
        this.idHorarioAula = idHorarioAula;
    }

    public List<AlunoDto> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoDto> alunos) {
        this.alunos = alunos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

}
