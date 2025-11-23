package com.example.schollink.Dto;

import java.util.List;

public class TurmaRetornoDto {
    private List<AlunoDto> alunos;
    private List<TurmaDisciplinaDto> turmaDisciplinaDtos;
    public List<AlunoDto> getAlunos() {
        return alunos;
    }
    public void setAlunos(List<AlunoDto> alunos) {
        this.alunos = alunos;
    }
    public List<TurmaDisciplinaDto> getTurmaDisciplinaDtos() {
        return turmaDisciplinaDtos;
    }
    public void setTurmaDisciplinaDtos(List<TurmaDisciplinaDto> turmaDisciplinaDtos) {
        this.turmaDisciplinaDtos = turmaDisciplinaDtos;
    }
    
}
