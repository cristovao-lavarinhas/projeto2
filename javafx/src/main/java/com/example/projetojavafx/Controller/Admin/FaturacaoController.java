package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Fatura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FaturacaoController {

    @FXML
    private TableView<Fatura> faturaTable;
    @FXML
    private TableColumn<Fatura, LocalDate> dataColumn;
    @FXML
    private TableColumn<Fatura, Double> valorColumn;
    @FXML
    private TableColumn<Fatura, String> descricaoColumn;
    @FXML
    private ComboBox<String> motoristaCombo;
    @FXML
    private Label totalLabel;
    @FXML
    private final ObservableList<Fatura> todasFaturas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Mapear colunas da tabela
        dataColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getData()));
        valorColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getValor()));
        descricaoColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDescricao()));

        // Adicionar motoristas à combo
        motoristaCombo.setItems(FXCollections.observableArrayList("João Silva", "Ana Costa", "Carlos Santos"));
        motoristaCombo.setOnAction(e -> atualizarTabela());

        // Dados de teste
        todasFaturas.addAll(
                new Fatura("João Silva", LocalDate.of(2024, 4, 5), 150.00, "Serviço Lisboa-Porto"),
                new Fatura("Ana Costa", LocalDate.of(2024, 4, 6), 200.00, "Transporte VIP"),
                new Fatura("João Silva", LocalDate.of(2024, 4, 8), 75.00, "Viagem curta")
        );
    }

    private void atualizarTabela() {
        String motorista = motoristaCombo.getValue();
        if (motorista == null) return;

        ObservableList<Fatura> filtradas = todasFaturas.filtered(f -> f.getMotorista().equals(motorista));
        faturaTable.setItems(filtradas);

        double total = filtradas.stream().mapToDouble(Fatura::getValor).sum();
        totalLabel.setText(String.format("%.2f€", total));
    }

    @FXML
    private void onNovaFatura() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Nova Fatura");
        alerta.setHeaderText(null);
        alerta.setContentText("Funcionalidade de criação de fatura ainda não está implementada.");
        alerta.showAndWait();
    }
}
