package com.example.projetojavafx.Modelo;

public class Viatura {

    private String marca;
    private String modelo;
    private String matricula;
    private String ano;
    private String cor;
    private boolean seguroAtivo;
    private Viatura viaturaAssociada;

    public Viatura(String marca, String modelo, String matricula, String ano, String cor, boolean seguroAtivo) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.ano = ano;
        this.cor = cor;
        this.seguroAtivo = seguroAtivo;
    }

    // Getters
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getMatricula() { return matricula; }
    public String getAno() { return ano; }
    public String getCor() { return cor; }
    public boolean isSeguroAtivo() { return seguroAtivo; }

    // Setters
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setAno(String ano) { this.ano = ano; }
    public void setCor(String cor) { this.cor = cor; }
    public void setSeguroAtivo(boolean seguroAtivo) { this.seguroAtivo = seguroAtivo; }
}
