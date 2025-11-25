package com.example.schollink.Dto;

public class ConversaDto {
    private Long idConversa;
    private String nomeAluno;
    private Long idAluno;
    private String caminhoFoto;

    public String getCaminhoFoto() {
        return caminhoFoto;
    }
    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }
    public Long getIdConversa() {
        return idConversa;
    }
    public void setIdConversa(Long idConversa) {
        this.idConversa = idConversa;
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

    
}
