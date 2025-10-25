package com.example.schollink.Dto;

import java.time.LocalDate;


public class AlunoDto {
    private Long idAluno;
    private UserDto userDto;
    private String matricula;
    private LocalDate dataMatricula;
    private String statusMatricula;
    private String nomeResponsavel;
    private String telefoneResponsavel;
    private EnderecoDto enderecoDto;
    private boolean presenca;


    public Long getIdAluno() {
        return this.idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public boolean getPresenca() {
        return this.presenca;
    }

    public UserDto getUserDto() {
        return this.userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataMatricula() {
        return this.dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getStatusMatricula() {
        return this.statusMatricula;
    }

    public void setStatusMatricula(String statusMatricula) {
        this.statusMatricula = statusMatricula;
    }

    public String getNomeResponsavel() {
        return this.nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getTelefoneResponsavel() {
        return this.telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public EnderecoDto getEnderecoDto() {
        return this.enderecoDto;
    }

    public void setEnderecoDto(EnderecoDto enderecoDto) {
        this.enderecoDto = enderecoDto;
    }

    public boolean isPresenca() {
        return presenca;
    }

    public void setPresenca(boolean presenca) {
        this.presenca = presenca;
    }

    

}
