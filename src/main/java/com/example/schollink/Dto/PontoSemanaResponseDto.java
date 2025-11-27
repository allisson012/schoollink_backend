package com.example.schollink.Dto;

import java.util.List;

public class PontoSemanaResponseDto {
   private List<PontoRetornoDto> pontos;
   private String totalHoras;
public List<PontoRetornoDto> getPontos() {
    return pontos;
}
public void setPontos(List<PontoRetornoDto> pontos) {
    this.pontos = pontos;
}
public String getTotalHoras() {
    return totalHoras;
}
public void setTotalHoras(String totalHoras) {
    this.totalHoras = totalHoras;
} 
   
}
