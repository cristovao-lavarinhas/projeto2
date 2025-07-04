package com.example.javafxclient.model;

import java.math.BigDecimal;
import java.util.Date;

public class Viagem {
    private Long idViagem;
    private Date data;
    private Date horaInicio;
    private Date horaFim;
    private BigDecimal preco;
    private Motorista motorista;
    private Cliente cliente;

    public Viagem() {}

    public Viagem(Date data, Date horaInicio, Date horaFim, BigDecimal preco, Motorista motorista, Cliente cliente) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.preco = preco;
        this.motorista = motorista;
        this.cliente = cliente;
    }

    public Long getIdViagem() { return idViagem; }
    public void setIdViagem(Long idViagem) { this.idViagem = idViagem; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Date getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Date horaInicio) { this.horaInicio = horaInicio; }

    public Date getHoraFim() { return horaFim; }
    public void setHoraFim(Date horaFim) { this.horaFim = horaFim; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return "Viagem " + idViagem + " - " + (motorista != null ? motorista.getNome() : "N/A") + 
               " → " + (cliente != null ? cliente.getNome() : "N/A") + " (" + preco + "€)";
    }
} 