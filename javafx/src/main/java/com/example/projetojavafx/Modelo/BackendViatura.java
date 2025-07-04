package com.example.projetojavafx.Modelo;

public class BackendViatura {
    private Long idViatura;
    private BackendMotorista motorista;
    private String matricula;
    private String modelo;
    private Integer ano;

    public BackendViatura() {}

    public BackendViatura(String matricula, String modelo, Integer ano) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.ano = ano;
    }

    // Getters and Setters
    public Long getIdViatura() { return idViatura; }
    public void setIdViatura(Long idViatura) { this.idViatura = idViatura; }

    public BackendMotorista getMotorista() { return motorista; }
    public void setMotorista(BackendMotorista motorista) { this.motorista = motorista; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    @Override
    public String toString() {
        return "BackendViatura{" +
                "idViatura=" + idViatura +
                ", matricula='" + matricula + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                '}';
    }
} 