package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    private String tipoDocumento;

    @Temporal(TemporalType.DATE)
    private Date dataExpiracao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public Date getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(Date dataExpiracao) { this.dataExpiracao = dataExpiracao; }
}