package com.example.javafxclient.model;

import java.util.Date;

public class Motorista {
    private Long idMotorista;
    private String nome;
    private String tel;
    private String cartaConducao;
    private Date dtNascimento;
    private Avaliacao avaliacao;

    public Motorista() {}

    public Motorista(String nome, String tel, String cartaConducao, Date dtNascimento) {
        this.nome = nome;
        this.tel = tel;
        this.cartaConducao = cartaConducao;
        this.dtNascimento = dtNascimento;
    }

    public Long getIdMotorista() { return idMotorista; }
    public void setIdMotorista(Long idMotorista) { this.idMotorista = idMotorista; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getCartaConducao() { return cartaConducao; }
    public void setCartaConducao(String cartaConducao) { this.cartaConducao = cartaConducao; }

    public Date getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(Date dtNascimento) { this.dtNascimento = dtNascimento; }

    public Avaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Avaliacao avaliacao) { this.avaliacao = avaliacao; }

    @Override
    public String toString() {
        return nome + " (" + cartaConducao + ")";
    }
} 