package com.example.projetojavafx.Service;

import com.example.projetojavafx.Modelo.Motorista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MotoristaService {

    private static ObservableList<Motorista> motoristas = FXCollections.observableArrayList();

    public static void adicionarMotorista(Motorista motorista) {
        motoristas.add(motorista);
        System.out.println("Motorista adicionado: " + motorista.getNome());
    }

    public static ObservableList<Motorista> listarMotoristas() {
        return motoristas;
    }
}
