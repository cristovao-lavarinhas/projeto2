package com.example.projetojavafx.Modelo;

import java.util.Date;

public class BackendMotorista {
    private Long idMotorista;
    private String nome;
    private String tel;
    private String cartaConducao;
    private Date dtNascimento;
    private BackendAvaliacao avaliacao;

    public BackendMotorista() {}

    public BackendMotorista(String nome, String tel, String cartaConducao, Date dtNascimento) {
        this.nome = nome;
        this.tel = tel;
        this.cartaConducao = cartaConducao;
        this.dtNascimento = dtNascimento;
    }

    // Getters and Setters
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

    public BackendAvaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(BackendAvaliacao avaliacao) { this.avaliacao = avaliacao; }

    @Override
    public String toString() {
        return "BackendMotorista{" +
                "idMotorista=" + idMotorista +
                ", nome='" + nome + '\'' +
                ", tel='" + tel + '\'' +
                ", cartaConducao='" + cartaConducao + '\'' +
                ", dtNascimento=" + dtNascimento +
                '}';
    }
} 