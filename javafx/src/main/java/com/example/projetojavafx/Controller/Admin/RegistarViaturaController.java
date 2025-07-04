package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Viatura;
import com.example.projetojavafx.Service.ViaturaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class RegistarViaturaController {

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private TextField matriculaField;

    @FXML
    private TextField anoField;

    @FXML
    private TextField corField;

    @FXML
    private CheckBox seguroCheckBox;

    @FXML
    private void handleGuardarViatura() {
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String matricula = matriculaField.getText();
        String ano = anoField.getText();
        String cor = corField.getText();
        boolean seguro = seguroCheckBox.isSelected();

        if (marca.isEmpty() || modelo.isEmpty() || matricula.isEmpty() || ano.isEmpty() || cor.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campos Obrigatórios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        Viatura viatura = new Viatura(marca, modelo, matricula, ano, cor, seguro);
        ViaturaService.adicionarViatura(viatura);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Viatura registada com sucesso!");
        alert.showAndWait();

        limparCampos();
    }

    private void limparCampos() {
        marcaField.clear();
        modeloField.clear();
        matriculaField.clear();
        anoField.clear();
        corField.clear();
        seguroCheckBox.setSelected(false);
    }
}
