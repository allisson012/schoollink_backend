package com.example.schollink.Dto;

import java.util.Map;

public class DisciplinaNotasDto {
    private String nomeDisciplina;

    private Map<String, Double> primeiroBimestre;
    private Map<String, Double> segundoBimestre;
    private Map<String, Double> terceiroBimestre;
    private Map<String, Double> quartoBimestre;

    private Double mediaPrimeiroBi;
    private Double mediaSegundoBi;
    private Double mediaTerceiroBi;
    private Double mediaQuartoBi;  
    private Double mediaFinal;

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }
    public Map<String, Double> getPrimeiroBimestre() {
        return primeiroBimestre;
    }
    public void setPrimeiroBimestre(Map<String, Double> primeiroBimestre) {
        this.primeiroBimestre = primeiroBimestre;
    }
    public Map<String, Double> getSegundoBimestre() {
        return segundoBimestre;
    }
    public void setSegundoBimestre(Map<String, Double> segundoBimestre) {
        this.segundoBimestre = segundoBimestre;
    }
    public Map<String, Double> getTerceiroBimestre() {
        return terceiroBimestre;
    }
    public void setTerceiroBimestre(Map<String, Double> terceiroBimestre) {
        this.terceiroBimestre = terceiroBimestre;
    }
    public Map<String, Double> getQuartoBimestre() {
        return quartoBimestre;
    }
    public void setQuartoBimestre(Map<String, Double> quartoBimestre) {
        this.quartoBimestre = quartoBimestre;
    }
    public Double getMediaPrimeiroBi() {
        return mediaPrimeiroBi;
    }
    public void setMediaPrimeiroBi(Double mediaPrimeiroBi) {
        this.mediaPrimeiroBi = mediaPrimeiroBi;
    }
    public Double getMediaSegundoBi() {
        return mediaSegundoBi;
    }
    public void setMediaSegundoBi(Double mediaSegundoBi) {
        this.mediaSegundoBi = mediaSegundoBi;
    }
    public Double getMediaTerceiroBi() {
        return mediaTerceiroBi;
    }
    public void setMediaTerceiroBi(Double mediaTerceiroBi) {
        this.mediaTerceiroBi = mediaTerceiroBi;
    }
    public Double getMediaQuartoBi() {
        return mediaQuartoBi;
    }
    public void setMediaQuartoBi(Double mediaQuartoBi) {
        this.mediaQuartoBi = mediaQuartoBi;
    }
    public Double getMediaFinal() {
        return mediaFinal;
    }
    public void setMediaFinal(Double mediaFinal) {
        this.mediaFinal = mediaFinal;
    }    
}
