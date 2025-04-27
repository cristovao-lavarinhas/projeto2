package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "viagem")
@SequenceGenerator(name = "ViagemIdGen", sequenceName = "idViagemSeq", allocationSize = 1)
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ViagemIdGen")
    @Column(name = "idViagem", nullable = false)
    private Long idViagem;

    @Temporal(TemporalType.DATE)
    @Column(name = "data", nullable = false)
    private Date data;

    @Temporal(TemporalType.TIME)
    @Column(name = "horaInicio", nullable = false)
    private Date horaInicio;

    @Temporal(TemporalType.TIME)
    @Column(name = "horaFim")
    private Date horaFim;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "idMotorista", nullable = false)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    // Construtores
    public Viagem() {}

    public Viagem(Date data, Date horaInicio, Date horaFim, BigDecimal preco, Motorista motorista, Cliente cliente) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.preco = preco;
        this.motorista = motorista;
        this.cliente = cliente;
    }

    // Getters e Setters
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
        return "Viagem{" +
                "idViagem=" + idViagem +
                ", data=" + data +
                ", horaInicio=" + horaInicio +
                ", horaFim=" + horaFim +
                ", preco=" + preco +
                ", motorista=" + motorista +
                ", cliente=" + cliente +
                '}';
    }
}
