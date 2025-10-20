package com.example.schollink.Dto;

public class DisciplinaDto {
    private Long id;
    private String nome;
    private Long idProfessor;
    
    public DisciplinaDto(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Long getIdProfessor() {
        return idProfessor;
    }
    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }
    
}
