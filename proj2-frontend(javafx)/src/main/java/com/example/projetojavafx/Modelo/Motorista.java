package com.example.projetojavafx.Modelo;

public class Motorista {

    private String nome;
    private String telefone;
    private String licenca;
    private String estado;
    private DocumentoMotorista documentos = new DocumentoMotorista();
    private Viatura viaturaAssociada;

    public Motorista(String nome, String telefone, String licenca, String estado) {
        this.nome = nome;
        this.telefone = telefone;
        this.licenca = licenca;
        this.estado = estado;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getLicenca() {
        return licenca;
    }

    public String getEstado() {
        return estado;
    }

    public Viatura getViatura() {
        return viaturaAssociada;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DocumentoMotorista getDocumentos() {
        return documentos;
    }

    public void setViatura(Viatura viaturaAssociada) {
        this.viaturaAssociada = viaturaAssociada;
    }
}
