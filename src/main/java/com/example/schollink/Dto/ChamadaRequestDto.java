package com.example.schollink.Dto;

import java.util.List;

public class ChamadaRequestDto {
    private Long idHorarioAula;
    private List<AlunoDto> alunos;

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

}
