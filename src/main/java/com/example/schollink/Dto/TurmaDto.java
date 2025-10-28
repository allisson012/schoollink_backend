package com.example.schollink.Dto;

import java.util.List;

import com.example.schollink.model.Anos;

public class TurmaDto {
    private String nome;
    private Anos anoEscolar;
    private int anoLetivo;
    private List<Integer> idAlunos;
    private List<DisciplinaProfessorDto> disciplinas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Anos getAnoEscolar() {
        return anoEscolar;
    }

    public void setAnoEscolar(Anos anoEscolar) {
        this.anoEscolar = anoEscolar;
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public List<Integer> getIdAlunos() {
        return idAlunos;
    }

    public void setIdAlunos(List<Integer> idAlunos) {
        this.idAlunos = idAlunos;
    }

    public List<DisciplinaProfessorDto> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<DisciplinaProfessorDto> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
