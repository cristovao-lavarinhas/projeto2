package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.PedidoInscricao;
import com.example.projetojavafx.Service.ApiClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class PedidosInscricaoController {

    @FXML
    private TableView<PedidoInscricao> pedidosTable;

    @FXML
    private TableColumn<PedidoInscricao, String> nomeColumn;

    @FXML
    private TableColumn<PedidoInscricao, String> emailColumn;

    @FXML
    private TableColumn<PedidoInscricao, String> telefoneColumn;

    @FXML
    private TableColumn<PedidoInscricao, String> estadoColumn;

    @FXML
    private TableColumn<PedidoInscricao, Void> acaoColumn;

    private ObservableList<PedidoInscricao> pedidosList;

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("tel")); // ou "telefone"
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        carregarPedidosReais();
        addAcoes();
    }

    private void carregarPedidosReais() {
        ApiClient.getList("/auth/admin/pedidos-inscricao", PedidoInscricao.class)
            .thenAccept(pedidos -> {
                if (pedidos != null) {
                    javafx.application.Platform.runLater(() -> pedidosTable.setItems(FXCollections.observableArrayList(pedidos)));
                }
            });
    }

    private void addAcoes() {
        Callback<TableColumn<PedidoInscricao, Void>, TableCell<PedidoInscricao, Void>> cellFactory = (param) -> new TableCell<>() {
            private final Button approveBtn = new Button("✅");
            private final Button rejectBtn = new Button("❌");

            {
                approveBtn.getStyleClass().add("approve-button");
                rejectBtn.getStyleClass().add("reject-button");

                approveBtn.setOnAction(event -> {
                    PedidoInscricao pedido = getTableView().getItems().get(getIndex());
                    java.util.Map<String, Object> payload = new java.util.HashMap<>();
                    payload.put("idMotorista", pedido.getId());
                    payload.put("estado", "APROVADO");
                    ApiClient.postText("/auth/admin/validar-inscricao", payload)
                        .thenAccept(resp -> {
                            pedido.setEstado("APROVADO");
                            javafx.application.Platform.runLater(() -> pedidosTable.refresh());
                        });
                });

                rejectBtn.setOnAction(event -> {
                    PedidoInscricao pedido = getTableView().getItems().get(getIndex());
                    java.util.Map<String, Object> payload = new java.util.HashMap<>();
                    payload.put("idMotorista", pedido.getId());
                    payload.put("estado", "REJEITADO");
                    ApiClient.postText("/auth/admin/validar-inscricao", payload)
                        .thenAccept(resp -> {
                            pedido.setEstado("REJEITADO");
                            javafx.application.Platform.runLater(() -> pedidosTable.refresh());
                        });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(approveBtn, rejectBtn);
                    box.setSpacing(10);
                    box.setAlignment(Pos.CENTER);
                    setGraphic(box);
                }
            }
        };
        acaoColumn.setCellFactory(cellFactory);
    }
}
