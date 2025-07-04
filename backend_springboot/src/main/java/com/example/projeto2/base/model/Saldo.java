package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "saldo")
public class Saldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    private BigDecimal saldo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
}