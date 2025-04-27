package com.example.projetojavafx.Controller;

import com.example.projetojavafx.Modelo.Motorista;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

public class ListarMotoristasController {

    @FXML
    private TableView<Motorista> driversTable;

    @FXML
    private TableColumn<Motorista, String> nameColumn;

    @FXML
    private TableColumn<Motorista, String> phoneColumn;

    @FXML
    private TableColumn<Motorista, String> licenseColumn;

    @FXML
    private TableColumn<Motorista, String> statusColumn;

    @FXML
    private TableColumn<Motorista, Void> editColumn;

    @FXML
    private TableColumn<Motorista, Void> deleteColumn;

    private ObservableList<Motorista> motoristas;

    @FXML
    public void initialize() {
        // Definição das propriedades de cada coluna
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        licenseColumn.setCellValueFactory(new PropertyValueFactory<>("licenca"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Dados fictícios de exemplo
        motoristas = FXCollections.observableArrayList(
                new Motorista("João Silva", "912345678", "TVDE12345", "Ativo"),
                new Motorista("Ana Costa", "934567890", "TVDE54321", "Inativo"),
                new Motorista("Carlos Santos", "926789012", "TVDE67890", "Ativo")
        );

        driversTable.setItems(motoristas);

        driversTable.setEditable(true);

        // Torna as colunas de texto editáveis
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        licenseColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Torna a coluna "Estado" uma ChoiceBox editável
        statusColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn("Ativo", "Inativo"));

        // Atualiza o objeto Motorista ao editar
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
                event.getRowValue().setNome(event.getNewValue())
        );
        phoneColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
                event.getRowValue().setTelefone(event.getNewValue())
        );
        licenseColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
                event.getRowValue().setLicenca(event.getNewValue())
        );
        statusColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
                event.getRowValue().setEstado(event.getNewValue())
        );


        // Adicionar botões de ação
        addEditButton();
        addDeleteButton();
    }

    // Botão Editar com ícone
    private void addEditButton() {
        Callback<TableColumn<Motorista, Void>, TableCell<Motorista, Void>> cellFactory = (param) -> new TableCell<>() {
            private final Button btn = new Button("", new FontIcon("fas-pencil-alt"));

            {
                btn.getStyleClass().add("edit-icon-button");
                btn.setOnAction(event -> {
                    Motorista motorista = getTableView().getItems().get(getIndex());
                    abrirFormularioEditar(motorista);
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
        Callback<TableColumn<Motorista, Void>, TableCell<Motorista, Void>> cellFactory = (param) -> new TableCell<>() {
            private final Button btn = new Button("", new FontIcon("fas-trash-alt"));

            {
                btn.getStyleClass().add("delete-icon-button");
                btn.setOnAction(event -> {
                    Motorista motorista = getTableView().getItems().get(getIndex());
                    eliminarMotorista(motorista);
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

    // Método de adicionar novo motorista (podes expandir no futuro)
    @FXML
    private void adicionarMotorista() {
        Motorista novoMotorista = new Motorista("Novo Motorista", "900000000", "TVDE00000", "Ativo");
        motoristas.add(novoMotorista);
    }

    // Método editar (no futuro podes abrir popup para mais campos)
    private void abrirFormularioEditar(Motorista motorista) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editar Motorista");
        alert.setHeaderText(null);
        alert.setContentText("Pode editar diretamente na tabela!");
        alert.showAndWait();
    }

    // Método eliminar com confirmação
    private void eliminarMotorista(Motorista motorista) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminação");
        alert.setHeaderText("Tem certeza que deseja eliminar este motorista?");
        alert.setContentText(motorista.getNome());

        if (alert.showAndWait().get() == ButtonType.OK){
            motoristas.remove(motorista);
        }
    }
}
