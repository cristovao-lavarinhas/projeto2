package com.example.projetojavafx.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Motorista {

    private String nome;
    @JsonProperty("tel")
    private String telefone;
    @JsonProperty("cartaConducao")
    private String licenca;
    private String estado;
    private String email;
    private String morada;
    private String iban;
    private DocumentoMotorista documentos = new DocumentoMotorista();
    private Viatura viaturaAssociada;
    private Long id;

    public Motorista() {
        // Necessário para o Jackson
    }

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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada; }
    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public Viatura getViatura() {
        return viaturaAssociada;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return nome;
    }
}
