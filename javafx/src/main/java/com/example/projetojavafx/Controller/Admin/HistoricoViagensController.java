package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Viagem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoricoViagensController {

    @FXML
    private TableView<Viagem> historicoViagensTable;

    @FXML
    private TableColumn<Viagem, String> motoristaColumn;

    @FXML
    private TableColumn<Viagem, String> partidaColumn;

    @FXML
    private TableColumn<Viagem, String> destinoColumn;

    @FXML
    private TableColumn<Viagem, String> dataViagemColumn;

    @FXML
    private TableColumn<Viagem, String> estadoColumn;

    @FXML
    private TableColumn<Viagem, Void> detalhesColumn;

    @FXML
    private ComboBox<String> estadoFilterCombo;

    private ObservableList<Viagem> viagensList;

    @FXML
    private void initialize() {
        // Configurar colunas
        motoristaColumn.setCellValueFactory(new PropertyValueFactory<>("motorista"));
        partidaColumn.setCellValueFactory(new PropertyValueFactory<>("partida"));
        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        dataViagemColumn.setCellValueFactory(new PropertyValueFactory<>("dataViagem"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Preencher dados
        viagensList = FXCollections.observableArrayList(
                new Viagem("João Silva", "Lisboa", "Porto", "2024-04-01", "Concluída"),
                new Viagem("Ana Costa", "Faro", "Lisboa", "2024-04-02", "Cancelada"),
                new Viagem("Carlos Santos", "Coimbra", "Braga", "2024-04-03", "Concluída")
        );
        historicoViagensTable.setItems(viagensList);

        // Cor na coluna "Estado"
        estadoColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    if (estado.equals("Concluída")) {
                        setStyle("-fx-background-color: #e6ffe6;");
                    } else if (estado.equals("Cancelada")) {
                        setStyle("-fx-background-color: #ffe6e6;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        // Botão "Ver" por linha
        detalhesColumn.setCellFactory(coluna -> new TableCell<>() {
            private final Button btn = new Button("Ver");

            {
                btn.setOnAction(event -> {
                    Viagem viagem = getTableView().getItems().get(getIndex());
                    abrirPopupDetalhes(viagem);
                });
                btn.setStyle("-fx-font-size: 11px; -fx-padding: 2 6 2 6;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        // Filtro por estado
        estadoFilterCombo.setOnAction(e -> {
            String estadoSelecionado = estadoFilterCombo.getValue();
            if (estadoSelecionado == null || estadoSelecionado.equals("Todos")) {
                historicoViagensTable.setItems(viagensList);
            } else {
                ObservableList<Viagem> filtradas = viagensList.filtered(
                        v -> v.getEstado().equals(estadoSelecionado)
                );
                historicoViagensTable.setItems(filtradas);
            }
        });

        // Duplo clique ainda funciona
        historicoViagensTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !historicoViagensTable.getSelectionModel().isEmpty()) {
                Viagem viagemSelecionada = historicoViagensTable.getSelectionModel().getSelectedItem();
                abrirPopupDetalhes(viagemSelecionada);
            }
        });
    }

    private void abrirPopupDetalhes(Viagem viagem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Admin/detalhes_viagem.fxml"));
            Parent root = loader.load();

            DetalhesViagemController controller = loader.getController();
            Stage popupStage = new Stage();
            controller.setStage(popupStage);
            controller.setViagem(viagem);

            popupStage.setTitle("Detalhes da Viagem");
            popupStage.setScene(new Scene(root, 350, 300));
            popupStage.setResizable(false);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onVerDetalhesClick() {
        Viagem viagemSelecionada = historicoViagensTable.getSelectionModel().getSelectedItem();
        if (viagemSelecionada != null) {
            abrirPopupDetalhes(viagemSelecionada);
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Aviso");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleciona uma viagem primeiro.");
            alerta.showAndWait();
        }
    }
}
