package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Motorista;
import com.example.projetojavafx.Service.MotoristaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class EstadoMotoristaController {

    @FXML
    private TableView<Motorista> motoristasTable;

    @FXML
    private TableColumn<Motorista, String> nomeColumn;

    @FXML
    private TableColumn<Motorista, String> telefoneColumn;

    @FXML
    private TableColumn<Motorista, String> licencaColumn;

    @FXML
    private TableColumn<Motorista, String> estadoColumn;

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        licencaColumn.setCellValueFactory(new PropertyValueFactory<>("licenca"));

        // Estado com ComboBox
        estadoColumn.setCellFactory(estadoCellFactory());

        // Carregar motoristas
        motoristasTable.setItems(MotoristaService.listarMotoristas());
    }

    private Callback<TableColumn<Motorista, String>, TableCell<Motorista, String>> estadoCellFactory() {
        return param -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("Ativo", "Suspenso", "Inativo");

                comboBox.setOnAction(event -> {
                    Motorista motorista = getTableView().getItems().get(getIndex());
                    motorista.setEstado(comboBox.getValue());

                    mostrarMensagemSucesso(motorista.getNome());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(item);
                    setGraphic(comboBox);
                }
            }
        };
    }

    private void mostrarMensagemSucesso(String nomeMotorista) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Estado Atualizado");
        alert.setHeaderText(null);
        alert.setContentText("O estado do motorista \"" + nomeMotorista + "\" foi atualizado com sucesso!");
        alert.showAndWait();
    }
}
