package com.example.projetojavafx.Modelo;

public class Documento {
    private String nomeMotorista;
    private String tipoDocumento;
    private String dataExpiracao;

    public Documento(String nomeMotorista, String tipoDocumento, String dataExpiracao) {
        this.nomeMotorista = nomeMotorista;
        this.tipoDocumento = tipoDocumento;
        this.dataExpiracao = dataExpiracao;
    }

    public String getNomeMotorista() { return nomeMotorista; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getDataExpiracao() { return dataExpiracao; }
}