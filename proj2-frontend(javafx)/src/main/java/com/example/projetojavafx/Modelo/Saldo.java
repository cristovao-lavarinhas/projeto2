package com.example.projetojavafx.Modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

public class Saldo {

    private final StringProperty motorista;
    private final DoubleProperty saldo;
    private final StringProperty status;

    public Saldo(String motorista, double saldo, String status) {
        this.motorista = new SimpleStringProperty(motorista);
        this.saldo = new SimpleDoubleProperty(saldo);
        this.status = new SimpleStringProperty(status);
    }

    public String getMotorista() {
        return motorista.get();
    }

    public StringProperty motoristaProperty() {
        return motorista;
    }

    public double getSaldo() {
        return saldo.get();
    }

    public DoubleProperty saldoProperty() {
        return saldo;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

}
