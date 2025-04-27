package com.example.projeto2.base.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipoPagamento")
public class TipoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoPagamento", nullable = false)
    private Long idTipoPagamento;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    public TipoPagamento() {}

    public TipoPagamento(String nome) {
        this.nome = nome;
    }

    public Long getIdTipoPagamento() { return idTipoPagamento; }
    public void setIdTipoPagamento(Long idTipoPagamento) { this.idTipoPagamento = idTipoPagamento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return "TipoPagamento{" +
                "idTipoPagamento=" + idTipoPagamento +
                ", nome='" + nome + '\'' +
                '}';
    }
}