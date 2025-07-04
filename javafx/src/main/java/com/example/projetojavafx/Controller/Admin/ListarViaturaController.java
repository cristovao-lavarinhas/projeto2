package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Viatura;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

public class ListarViaturaController {

    @FXML
    private TableView<Viatura> viaturasTable;

    @FXML
    private TableColumn<Viatura, String> marcaColumn;

    @FXML
    private TableColumn<Viatura, String> modeloColumn;

    @FXML
    private TableColumn<Viatura, String> matriculaColumn;

    @FXML
    private TableColumn<Viatura, String> anoColumn;

    @FXML
    private TableColumn<Viatura, String> corColumn;

    @FXML
    private TableColumn<Viatura, Boolean> seguroColumn;

    @FXML
    private TableColumn<Viatura, Void> editColumn;

    @FXML
    private TableColumn<Viatura, Void> deleteColumn;

    private ObservableList<Viatura> viaturasList;

    @FXML
    public void initialize() {
        viaturasTable.setEditable(true);

        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        anoColumn.setCellValueFactory(new PropertyValueFactory<>("ano"));
        corColumn.setCellValueFactory(new PropertyValueFactory<>("cor"));
        seguroColumn.setCellValueFactory(new PropertyValueFactory<>("seguroAtivo"));

        // Tornar editáveis
        marcaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modeloColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        matriculaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        anoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        corColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        seguroColumn.setCellFactory(CheckBoxTableCell.forTableColumn(seguroColumn));

        viaturasList = FXCollections.observableArrayList(
                new Viatura("Opel", "Corsa", "00-AA-11", "2002", "Branco", false)
        );

        viaturasTable.setItems(viaturasList);

        configurarEventosEdicao();

        addEditButton();
        addDeleteButton();
    }

    private void configurarEventosEdicao() {
        marcaColumn.setOnEditCommit(event -> event.getRowValue().setMarca(event.getNewValue()));
        modeloColumn.setOnEditCommit(event -> event.getRowValue().setModelo(event.getNewValue()));
        matriculaColumn.setOnEditCommit(event -> event.getRowValue().setMatricula(event.getNewValue()));
        anoColumn.setOnEditCommit(event -> event.getRowValue().setAno(event.getNewValue()));
        corColumn.setOnEditCommit(event -> event.getRowValue().setCor(event.getNewValue()));
    }

    // Botão Editar com ícone
    private void addEditButton() {
        Callback<TableColumn<Viatura, Void>, TableCell<Viatura, Void>> cellFactory = (param) -> new TableCell<>() {
            private final Button btn = new Button("", new FontIcon("fas-pencil-alt"));

            {
                btn.getStyleClass().add("edit-icon-button");
                btn.setOnAction(event -> {
                    Viatura viatura = getTableView().getItems().get(getIndex());
                    abrirFormularioEditar(viatura);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };
        editColumn.setCellFactory(cellFactory);
    }

    // Botão Eliminar com ícone
    private void addDeleteButton() {
        Callback<TableColumn<Viatura, Void>, TableCell<Viatura, Void>> cellFactory = (param) -> new TableCell<>() {
            private final Button btn = new Button("", new FontIcon("fas-trash-alt"));

            {
                btn.getStyleClass().add("delete-icon-button");
                btn.setOnAction(event -> {
                    Viatura viatura = getTableView().getItems().get(getIndex());
                    eliminarViatura(viatura);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };
        deleteColumn.setCellFactory(cellFactory);
    }

    // Método para mostrar alerta ao "Editar"
    private void abrirFormularioEditar(Viatura viatura) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editar Viatura");
        alert.setHeaderText(null);
        alert.setContentText("Pode editar diretamente na tabela!");
        alert.showAndWait();
    }

    // Método para eliminar viatura
    private void eliminarViatura(Viatura viatura) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminação");
        alert.setHeaderText("Tem certeza que deseja eliminar esta viatura?");
        alert.setContentText(viatura.getMarca() + " " + viatura.getModelo());

        if (alert.showAndWait().get() == ButtonType.OK){
            viaturasList.remove(viatura);
        }
    }
}
