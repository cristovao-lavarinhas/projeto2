package com.example.javafxclient.model;

public class CodigoPostal {
    private Long idCodPostal;
    private String codigo;
    private String localidade;

    public CodigoPostal() {}

    public CodigoPostal(String codigo, String localidade) {
        this.codigo = codigo;
        this.localidade = localidade;
    }

    public Long getIdCodPostal() { return idCodPostal; }
    public void setIdCodPostal(Long idCodPostal) { this.idCodPostal = idCodPostal; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }

    @Override
    public String toString() {
        return codigo + " - " + localidade;
    }
} 