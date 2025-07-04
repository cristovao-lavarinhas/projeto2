package com.example.projetojavafx.Modelo;

public class PedidoInscricao {

    private String nome;
    private String email;
    private String telefone;
    private String estado;

    public PedidoInscricao(String nome, String email, String telefone, String estado) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.estado = estado;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }
}
