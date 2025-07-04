package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.EstadoViatura;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class EstadoViaturaController {

    @FXML
    private TableView<EstadoViatura> estadoViaturasTable;

    @FXML
    private TableColumn<EstadoViatura, String> marcaColumn;

    @FXML
    private TableColumn<EstadoViatura, String> modeloColumn;

    @FXML
    private TableColumn<EstadoViatura, String> matriculaColumn;

    @FXML
    private TableColumn<EstadoViatura, String> estadoColumn;

    @FXML
    private TableColumn<EstadoViatura, String> inspecaoColumn;

    @FXML
    private TableColumn<EstadoViatura, Void> acoesColumn;

    private ObservableList<EstadoViatura> viaturasList;

    @FXML
    private void initialize() {
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        inspecaoColumn.setCellValueFactory(new PropertyValueFactory<>("ultimaInspecao"));

        viaturasList = FXCollections.observableArrayList(
                new EstadoViatura("Opel", "Corsa", "00-AA-11", "Ativo", "2023-05-15"),
                new EstadoViatura("Toyota", "Yaris", "11-BB-22", "Inativo", "2022-11-10")
        );

        estadoViaturasTable.setItems(viaturasList);

        addAcoesButtons();
    }

    private void addAcoesButtons() {
        Callback<TableColumn<EstadoViatura, Void>, TableCell<EstadoViatura, Void>> cellFactory = (param) -> new TableCell<>() {
            private final Button aprovarBtn = new Button("", new FontIcon("fas-check"));
            private final Button marcarProblemaBtn = new Button("", new FontIcon("fas-times"));
            private final HBox buttons = new HBox(aprovarBtn, marcarProblemaBtn);

            {
                aprovarBtn.getStyleClass().add("approve-button");
                marcarProblemaBtn.getStyleClass().add("reject-button");

                aprovarBtn.setOnAction(event -> {
                    EstadoViatura viatura = getTableView().getItems().get(getIndex());
                    viatura.setEstado("Ativo");
                    estadoViaturasTable.refresh();
                });

                marcarProblemaBtn.setOnAction(event -> {
                    EstadoViatura viatura = getTableView().getItems().get(getIndex());
                    viatura.setEstado("Inativo");
                    estadoViaturasTable.refresh();
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
