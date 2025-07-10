package com.example.projetojavafx.Controller.Admin;

import org.kordamp.ikonli.javafx.FontIcon;

import com.example.projetojavafx.Modelo.Motorista;
import com.example.projetojavafx.Service.ApiClient;
import com.example.projetojavafx.Service.MotoristaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

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

        // Carregar motoristas reais do backend
        ApiClient.getList("/motoristas", Motorista.class)
            .thenAccept(motoristas -> {
                System.out.println("Recebido do backend: " + motoristas);
                if (motoristas != null) {
                    javafx.application.Platform.runLater(() -> {
                        driversTable.setItems(FXCollections.observableArrayList(motoristas));
                    });
                }
            });

        driversTable.setEditable(false);
        // Remover cell factories editáveis
        // nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // licenseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // statusColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn("Ativo", "Inativo"));

        // Atualiza o objeto Motorista ao editar
        // nameColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
        //         event.getRowValue().setNome(event.getNewValue())
        // );
        // phoneColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
        //         event.getRowValue().setTelefone(event.getNewValue())
        // );
        // licenseColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
        //         event.getRowValue().setLicenca(event.getNewValue())
        // );
        // statusColumn.setOnEditCommit((TableColumn.CellEditEvent<Motorista, String> event) ->
        //         event.getRowValue().setEstado(event.getNewValue())
        // );


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
        MotoristaService.adicionarMotorista(novoMotorista);
    }

    // Método editar (agora abre modal)
    private void abrirFormularioEditar(Motorista motorista) {
        Dialog<Motorista> dialog = new Dialog<>();
        dialog.setTitle("Editar Motorista");
        dialog.setHeaderText("Editar dados do motorista");

        ButtonType gravarButtonType = new ButtonType("Gravar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().setAll(gravarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeField = new TextField(motorista.getNome());
        TextField telefoneField = new TextField(motorista.getTelefone());
        TextField licencaField = new TextField(motorista.getLicenca());
        ChoiceBox<String> estadoBox = new ChoiceBox<>();
        estadoBox.getItems().addAll("Ativo", "Inativo");
        estadoBox.setValue(motorista.getEstado());

        grid.addRow(0, new Label("Nome:"), nomeField);
        grid.addRow(1, new Label("Telefone:"), telefoneField);
        grid.addRow(2, new Label("Licença:"), licencaField);
        grid.addRow(3, new Label("Estado:"), estadoBox);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == gravarButtonType) {
                motorista.setNome(nomeField.getText());
                motorista.setTelefone(telefoneField.getText());
                motorista.setLicenca(licencaField.getText());
                motorista.setEstado(estadoBox.getValue());
                driversTable.refresh();
                return motorista;
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Método eliminar com confirmação
    private void eliminarMotorista(Motorista motorista) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminação");
        alert.setHeaderText("Tem certeza que deseja eliminar este motorista?");
        alert.setContentText(motorista.getNome());

        if (alert.showAndWait().get() == ButtonType.OK){
            int index = motoristas.indexOf(motorista);
            if (index >= 0) {
                MotoristaService.deletarMotorista(index);
            }
        }
    }
}
