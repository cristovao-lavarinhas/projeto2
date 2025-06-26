package com.example.projetojavafx.Modelo;

public class EstadoViatura {
    private String marca;
    private String modelo;
    private String matricula;
    private String estado;
    private String ultimaInspecao;

    public EstadoViatura(String marca, String modelo, String matricula, String estado, String ultimaInspecao) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.estado = estado;
        this.ultimaInspecao = ultimaInspecao;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getMatricula() { return matricula; }
    public String getEstado() { return estado; }
    public String getUltimaInspecao() { return ultimaInspecao; }

    public void setEstado(String estado) { this.estado = estado; }
}
