package com.example.projetojavafx.Modelo;

import java.time.LocalDate;

public class Fatura {
    private String motorista;
    private LocalDate data;
    private double valor;
    private String descricao;

    public Fatura(String motorista, LocalDate data, double valor, String descricao) {
        this.motorista = motorista;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getMotorista() { return motorista; }
    public LocalDate getData() { return data; }
    public double getValor() { return valor; }
    public String getDescricao() { return descricao; }

    public void setMotorista(String motorista) { this.motorista = motorista; }
    public void setData(LocalDate data) { this.data = data; }
    public void setValor(double valor) { this.valor = valor; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
