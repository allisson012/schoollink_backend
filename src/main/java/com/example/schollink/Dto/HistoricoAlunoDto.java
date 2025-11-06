package com.example.schollink.Dto;

import java.util.List;

public class HistoricoAlunoDto {
    private String nomeAluno;
    private String matricula;
    private String turma;
    private List<DisciplinaNotasDto> disciplinas;
    
    public String getNomeAluno() {
        return nomeAluno;
    }
    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getTurma() {
        return turma;
    }
    public void setTurma(String turma) {
        this.turma = turma;
    }
    public List<DisciplinaNotasDto> getDisciplinas() {
    return disciplinas;
    }
    public void setDisciplinas(List<DisciplinaNotasDto> disciplinas) {
    this.disciplinas = disciplinas;
    }
}
