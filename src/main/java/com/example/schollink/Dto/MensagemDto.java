package com.example.schollink.Dto;

public class MensagemDto {
    private Long idMensagem;
    private String mensagem;
    private String nomeAluno;
    private Long idAluno;
    private Long idRemetente;
    public Long getIdMensagem() {
        return idMensagem;
    }
    public void setIdMensagem(Long idMensagem) {
        this.idMensagem = idMensagem;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public String getNomeAluno() {
        return nomeAluno;
    }
    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
    public Long getIdAluno() {
        return idAluno;
    }
    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }
    public Long getIdRemetente() {
        return idRemetente;
    }
    public void setIdRemetente(Long idRemetente) {
        this.idRemetente = idRemetente;
    }
    
}
