package com.example.javafxclient.model;

import java.util.Date;

public class Cliente {
    private Long idCliente;
    private String nome;
    private String nif;
    private String tel;
    private String rua;
    private Integer nPorta;
    private Avaliacao avaliacao;
    private CodigoPostal codigoPostal;

    // Constructors
    public Cliente() {}

    public Cliente(String nome, String nif, String tel, String rua, Integer nPorta) {
        this.nome = nome;
        this.nif = nif;
        this.tel = tel;
        this.rua = rua;
        this.nPorta = nPorta;
    }

    // Getters and Setters
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public Integer getNPorta() { return nPorta; }
    public void setNPorta(Integer nPorta) { this.nPorta = nPorta; }

    public Avaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Avaliacao avaliacao) { this.avaliacao = avaliacao; }

    public CodigoPostal getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(CodigoPostal codigoPostal) { this.codigoPostal = codigoPostal; }

    @Override
    public String toString() {
        return nome + " (" + nif + ")";
    }
} 