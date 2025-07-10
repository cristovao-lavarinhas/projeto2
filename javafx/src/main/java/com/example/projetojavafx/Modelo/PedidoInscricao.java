package com.example.projetojavafx.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoInscricao {
    private Long id;
    private String nome;
    private String tel;
    private String estado;

    public PedidoInscricao() {}

    public PedidoInscricao(Long id, String nome, String tel, String estado) {
        this.id = id;
        this.nome = nome;
        this.tel = tel;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
