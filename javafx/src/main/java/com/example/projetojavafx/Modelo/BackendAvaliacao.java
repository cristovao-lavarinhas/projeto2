package com.example.projetojavafx.Modelo;

public class BackendAvaliacao {
    private Long idAvaliacao;
    private Integer nota;
    private String comentario;

    public BackendAvaliacao() {}

    public BackendAvaliacao(Integer nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
    }

    // Getters and Setters
    public Long getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Long idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    @Override
    public String toString() {
        return "BackendAvaliacao{" +
                "idAvaliacao=" + idAvaliacao +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                '}';
    }
} 