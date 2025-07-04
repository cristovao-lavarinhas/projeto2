package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Motorista;
import com.example.projetojavafx.Modelo.Viatura;
import com.example.projetojavafx.Service.MotoristaService;
import com.example.projetojavafx.Service.ViaturaService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;

public class AssociarViaturaController {

    @FXML
    private ComboBox<Motorista> motoristaComboBox;

    @FXML
    private ComboBox<Viatura> viaturaComboBox;

    @FXML
    private void initialize() {
        motoristaComboBox.setItems(MotoristaService.listarMotoristas());
        viaturaComboBox.setItems(ViaturaService.listarViaturas());
    }

    @FXML
    private void handleAssociar() {
        Motorista motorista = motoristaComboBox.getValue();
        Viatura viatura = viaturaComboBox.getValue();

        if (motorista == null || viatura == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção obrigatória");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecione um motorista e uma viatura.");
            alert.showAndWait();
            return;
        }

        motorista.setViatura(viatura);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Viatura associada com sucesso ao motorista " + motorista.getNome() + "!");
        alert.showAndWait();
    }
}
