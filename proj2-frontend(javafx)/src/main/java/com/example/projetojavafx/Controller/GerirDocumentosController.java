package com.example.projetojavafx.Controller;

import com.example.projetojavafx.Modelo.Documento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

public class GerirDocumentosController {

    @FXML
    private TableView<Documento> documentosTable;

    @FXML
    private TableColumn<Documento, String> nomeColumn;

    @FXML
    private TableColumn<Documento, String> tipoDocColumn;

    @FXML
    private TableColumn<Documento, String> expiracaoColumn;

    @FXML
    private TableColumn<Documento, String> estadoColumn;

    @FXML
    private TableColumn<Documento, Void> acoesColumn;

    private ObservableList<Documento> documentosList;

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nomeMotorista"));
        tipoDocColumn.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));
        expiracaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataExpiracao"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        documentosList = FXCollections.observableArrayList(
                new Documento("João Silva", "Carta de Condução", "2025-08-15", "Pendente"),
                new Documento("Ana Costa", "Registo Criminal", "2026-03-10", "Pendente")
        );

        documentosTable.setItems(documentosList);

        addAcoesButtons();
    }

    private void addAcoesButtons() {
        Callback<TableColumn<Documento, Void>, TableCell<Documento, Void>> cellFactory = (param) -> new TableCell<>() {
            private final Button aprovarBtn = new Button("", new FontIcon("fas-check"));
            private final Button rejeitarBtn = new Button("", new FontIcon("fas-times"));
            private final HBox buttons = new HBox(aprovarBtn, rejeitarBtn);

            {
                aprovarBtn.getStyleClass().add("approve-button");
                rejeitarBtn.getStyleClass().add("reject-button");

                aprovarBtn.setOnAction(event -> {
                    Documento documento = getTableView().getItems().get(getIndex());
                    documento.setEstado("Aprovado");
                    documentosTable.refresh();
                });

                rejeitarBtn.setOnAction(event -> {
                    Documento documento = getTableView().getItems().get(getIndex());
                    documento.setEstado("Rejeitado");
                    documentosTable.refresh();
                });

                buttons.setSpacing(10);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttons);
                }
            }
        };
        acoesColumn.setCellFactory(cellFactory);
    }
}
