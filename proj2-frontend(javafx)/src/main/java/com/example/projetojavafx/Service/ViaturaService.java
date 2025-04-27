package com.example.projetojavafx.Service;

import com.example.projetojavafx.Modelo.Viatura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ViaturaService {

    private static ObservableList<Viatura> viaturas = FXCollections.observableArrayList();

    public static void adicionarViatura(Viatura viatura) {
        viaturas.add(viatura);
        System.out.println("Viatura registada: " + viatura.getMarca() + " " + viatura.getModelo());
    }

    public static ObservableList<Viatura> listarViaturas() {
        return viaturas;
    }
}
