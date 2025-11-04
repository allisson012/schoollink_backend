package com.example.schollink.Dto;

public class TurmaDisciplinaDto {
    private Long id_turma_disciplina;
    private Long id_professor;
    private DisciplinaDto disciplinaDto;
    private Long id_turma;


    public Long getIdTurmaDisciplina() {
        return this.id_turma_disciplina;
    }

    public void setIdTurmaDisciplina(Long id_turma_disciplina) {
        this.id_turma_disciplina = id_turma_disciplina;
    }

    public Long getId_professor() {
        return this.id_professor;
    }

    public void setId_professor(Long id_professor) {
        this.id_professor = id_professor;
    }


    public DisciplinaDto getDisciplinaDto() {
        return this.disciplinaDto;
    }

    public void setDisciplinaDto(DisciplinaDto disciplinaDto) {
        this.disciplinaDto = disciplinaDto;
    }


    public Long getId_turma() {
        return this.id_turma;
    }

    public void setId_turma(Long id_turma) {
        this.id_turma = id_turma;
    }

}
