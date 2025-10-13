package com.example.schollink.model;

public enum TipoProva {
    P1("Prova 1"),
    P2("Prova 2"),
    AC("Atividade Avaliativa"),
    AF("Atividade Formativa");

    private final String descricao;
    
    TipoProva(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
