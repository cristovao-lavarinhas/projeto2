package com.example.projeto2.base.model;

import jakarta.persistence.*;

@Entity
@Table(name = "documento_motorista")
public class DocumentoMotorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    private boolean licencaTvde;
    private boolean seguroViagem;
    private boolean documentoVeiculo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public boolean isLicencaTvde() { return licencaTvde; }
    public void setLicencaTvde(boolean licencaTvde) { this.licencaTvde = licencaTvde; }

    public boolean isSeguroViagem() { return seguroViagem; }
    public void setSeguroViagem(boolean seguroViagem) { this.seguroViagem = seguroViagem; }

    public boolean isDocumentoVeiculo() { return documentoVeiculo; }
    public void setDocumentoVeiculo(boolean documentoVeiculo) { this.documentoVeiculo = documentoVeiculo; }
}