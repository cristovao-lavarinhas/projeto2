package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Motorista;
import com.example.projetojavafx.Modelo.PedidoInscricao;
import com.example.projetojavafx.Service.MotoristaService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.layout.HBox;

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
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        pedidosList = FXCollections.observableArrayList(
                new PedidoInscricao("João Silva", "joao@gmail.com", "912345678", "Pendente"),
                new PedidoInscricao("Ana Costa", "ana@gmail.com", "934567890", "Pendente")
        );

        pedidosTable.setItems(pedidosList);

        addAcoes();
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
                    pedido.setEstado("Aprovado");
                    pedidosTable.refresh();

                    // Criar Motorista a partir do pedido
                    Motorista motorista = new Motorista(
                            pedido.getNome(),
                            pedido.getTelefone(),
                            "Licenca-" + pedido.getTelefone().substring(4), // Geração simples de licença
                            "Ativo"
                    );

                    MotoristaService.adicionarMotorista(motorista);
                });

                rejectBtn.setOnAction(event -> {
                    PedidoInscricao pedido = getTableView().getItems().get(getIndex());
                    pedido.setEstado("Rejeitado");
                    pedidosTable.refresh();
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
