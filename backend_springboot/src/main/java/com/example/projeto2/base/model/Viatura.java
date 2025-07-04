package com.example.projeto2.base.model;

import jakarta.persistence.*;

@Entity
@Table(name = "viatura")
public class Viatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idViatura", nullable = false)
    private Long idViatura;

    @ManyToOne
    @JoinColumn(name = "idMotorista", nullable = false)
    private Motorista motorista;

    @Column(name = "matricula", nullable = false, length = 20)
    private String matricula;

    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    // Construtores
    public Viatura() {}

    public Viatura(Motorista motorista, String matricula, String modelo, Integer ano) {
        this.motorista = motorista;
        this.matricula = matricula;
        this.modelo = modelo;
        this.ano = ano;
    }

    // Getters e Setters
    public Long getIdViatura() {
        return idViatura;
    }

    public void setIdViatura(Long idViatura) {
        this.idViatura = idViatura;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Viatura{" +
                "idViatura=" + idViatura +
                ", motorista=" + motorista +
                ", matricula='" + matricula + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                '}';
    }
}