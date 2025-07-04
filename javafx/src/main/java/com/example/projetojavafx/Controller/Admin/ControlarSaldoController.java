package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Saldo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControlarSaldoController {

    @FXML
    private ComboBox<String> motoristaCombo;

    @FXML
    private TableView<Saldo> saldoTable;

    @FXML
    private TableColumn<Saldo, String> motoristaColumn;

    @FXML
    private TableColumn<Saldo, Double> saldoColumn;

    @FXML
    private TableColumn<Saldo, String> statusColumn;

    @FXML
    private TableColumn<Saldo, String> acoesColumn;

    @FXML
    private Label totalPendenteLabel;

    private ObservableList<Saldo> saldoList;

    @FXML
    public void initialize() {
        // Iniciar a lista de saldos
        saldoList = FXCollections.observableArrayList(
                new Saldo("João Silva", 150.0, "Pendente"),
                new Saldo("Ana Costa", 75.0, "Pendente")
        );

        // Definir as colunas da tabela
        motoristaColumn.setCellValueFactory(cellData -> cellData.getValue().motoristaProperty());
        saldoColumn.setCellValueFactory(cellData -> cellData.getValue().saldoProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Adicionar botão para "Registrar Pagamento" na coluna Ações
        acoesColumn.setCellFactory(param -> new TableCell<Saldo, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Saldo saldoSelecionado = getTableRow().getItem();
                    if (saldoSelecionado == null) return;

                    Button pagamentoButton = new Button();
                    pagamentoButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-cursor: hand;");

                    atualizarBotaoTexto(pagamentoButton, saldoSelecionado);

                    pagamentoButton.setOnAction(e -> {
                        // Alternar entre "Pendente" e "Pago"
                        if (saldoSelecionado.getStatus().equals("Pendente")) {
                            saldoSelecionado.setStatus("Pago");
                        } else {
                            saldoSelecionado.setStatus("Pendente");
                        }
                        saldoTable.refresh();
                        atualizarBotaoTexto(pagamentoButton, saldoSelecionado);
                        atualizarTotal();
                    });

                    setGraphic(pagamentoButton);
                }
            }

            private void atualizarBotaoTexto(Button botao, Saldo saldo) {
                if (saldo.getStatus().equals("Pendente")) {
                    botao.setText("💰 Registrar Pagamento");
                } else {
                    botao.setText("🔄 Marcar como Pendente");
                }
            }
        });


        // Exibir saldos
        saldoTable.setItems(saldoList);

        // Exibir o total de saldo pendente
        double totalPendente = saldoList.stream().mapToDouble(Saldo::getSaldo).sum();
        totalPendenteLabel.setText(totalPendente + "€");
    }

    private void atualizarTotal() {
        double totalPendente = saldoList.stream()
                .filter(s -> s.getStatus().equals("Pendente"))
                .mapToDouble(Saldo::getSaldo)
                .sum();
        totalPendenteLabel.setText(String.format("%.2f€", totalPendente));
    }


    // Chamado quando clicas no botão do FXML
    @FXML
    private void registrarPagamento() {
        Saldo saldoSelecionado = saldoTable.getSelectionModel().getSelectedItem();
        if (saldoSelecionado != null && saldoSelecionado.getStatus().equals("Pendente")) {
            registrarPagamento(saldoSelecionado); // Reutiliza a lógica comum
        }
    }

    // Chamado quando clicas no botão da tabela
    private void registrarPagamento(Saldo saldo) {
        saldo.setStatus("Pago");
        saldoTable.refresh();

        double totalPendente = saldoList.stream()
                .filter(s -> s.getStatus().equals("Pendente"))
                .mapToDouble(Saldo::getSaldo)
                .sum();

        totalPendenteLabel.setText(totalPendente + "€");
    }



    //Método para filtrar os saldos por motorista
    @FXML
    private void filtrarSaldos() {
        String motoristaSelecionado = motoristaCombo.getValue();
        if (motoristaSelecionado != null) {
            ObservableList<Saldo> filtrados = FXCollections.observableArrayList(
                    saldoList.stream().filter(s -> s.getMotorista().equals(motoristaSelecionado)).toList()
            );
            saldoTable.setItems(filtrados);
        }
    }


}
