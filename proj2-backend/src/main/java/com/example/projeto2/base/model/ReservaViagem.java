package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reserva_viagem")
public class ReservaViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    private String partida;
    private String destino;

    @Temporal(TemporalType.DATE)
    private Date dataReserva;

    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAceite;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public String getPartida() { return partida; }
    public void setPartida(String partida) { this.partida = partida; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Date getDataReserva() { return dataReserva; }
    public void setDataReserva(Date dataReserva) { this.dataReserva = dataReserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getDataAceite() { return dataAceite; }
    public void setDataAceite(Date dataAceite) { this.dataAceite = dataAceite; }
}