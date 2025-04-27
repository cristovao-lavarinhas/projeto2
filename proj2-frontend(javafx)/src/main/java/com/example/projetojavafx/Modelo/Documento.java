package com.example.projetojavafx.Modelo;

public class Documento {
    private String nomeMotorista;
    private String tipoDocumento;
    private String dataExpiracao;
    private String estado;

    public Documento(String nomeMotorista, String tipoDocumento, String dataExpiracao, String estado) {
        this.nomeMotorista = nomeMotorista;
        this.tipoDocumento = tipoDocumento;
        this.dataExpiracao = dataExpiracao;
        this.estado = estado;
    }

    public String getNomeMotorista() { return nomeMotorista; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getDataExpiracao() { return dataExpiracao; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }
}
