package com.example.projetojavafx.Modelo;

public class DocumentoMotorista {

    private boolean licencaTVDE;
    private boolean seguroViagem;
    private boolean documentoVeiculo;

    public DocumentoMotorista() {
        this.licencaTVDE = false;
        this.seguroViagem = false;
        this.documentoVeiculo = false;
    }

    public boolean isLicencaTVDE() { return licencaTVDE; }
    public void setLicencaTVDE(boolean licencaTVDE) { this.licencaTVDE = licencaTVDE; }

    public boolean isSeguroViagem() { return seguroViagem; }
    public void setSeguroViagem(boolean seguroViagem) { this.seguroViagem = seguroViagem; }

    public boolean isDocumentoVeiculo() { return documentoVeiculo; }
    public void setDocumentoVeiculo(boolean documentoVeiculo) { this.documentoVeiculo = documentoVeiculo; }
}
