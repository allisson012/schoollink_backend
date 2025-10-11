package com.example.schollink.model;

public enum Anos {
    // Ensino Fundamental
    PRIMEIRO_FUNDAMENTAL("1º Ano - Fundamental"),
    SEGUNDO_FUNDAMENTAL("2º Ano - Fundamental"),
    TERCEIRO_FUNDAMENTAL("3º Ano - Fundamental"),
    QUARTO_FUNDAMENTAL("4º Ano - Fundamental"),
    QUINTO_FUNDAMENTAL("5º Ano - Fundamental"),
    SEXTO_FUNDAMENTAL("6º Ano - Fundamental"),
    SETIMO_FUNDAMENTAL("7º Ano - Fundamental"),
    OITAVO_FUNDAMENTAL("8º Ano - Fundamental"),
    NONO_FUNDAMENTAL("9º Ano - Fundamental"),

    // Ensino Médio
    PRIMEIRO_MEDIO("1º Ano - Médio"),
    SEGUNDO_MEDIO("2º Ano - Médio"),
    TERCEIRO_MEDIO("3º Ano - Médio");

    private final String descricao;

    Anos(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

