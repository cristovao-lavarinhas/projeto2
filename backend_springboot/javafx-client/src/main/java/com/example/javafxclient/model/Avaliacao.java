package com.example.javafxclient.model;

public class Avaliacao {
    private Long idAvaliacao;
    private Integer classificacao;
    private String comentario;

    public Avaliacao() {}

    public Avaliacao(Integer classificacao, String comentario) {
        this.classificacao = classificacao;
        this.comentario = comentario;
    }

    public Long getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Long idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public Integer getClassificacao() { return classificacao; }
    public void setClassificacao(Integer classificacao) { this.classificacao = classificacao; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    @Override
    public String toString() {
        return "Avaliação: " + classificacao + "/5";
    }
} 