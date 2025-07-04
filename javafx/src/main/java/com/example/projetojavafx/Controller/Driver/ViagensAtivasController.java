package com.example.projetojavafx.Controller.Driver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class ViagensAtivasController {

    @FXML
    private TableView<ViagemAtiva> viagensTable;
    @FXML
    private TableColumn<ViagemAtiva, String> dataColumn;
    @FXML
    private TableColumn<ViagemAtiva, String> clienteColumn;
    @FXML
    private TableColumn<ViagemAtiva, String> origemColumn;
    @FXML
    private TableColumn<ViagemAtiva, String> destinoColumn;
    @FXML
    private TableColumn<ViagemAtiva, String> statusColumn;
    @FXML
    private TableColumn<ViagemAtiva, Void> aceitarColumn;
    @FXML
    private TableColumn<ViagemAtiva, Void> recusarColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Label infoLabel;
    @FXML
    private ComboBox<String> estadoFilterCombo;
    @FXML
    private Label feedbackLabel;

    private ObservableList<ViagemAtiva> viagens = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        origemColumn.setCellValueFactory(new PropertyValueFactory<>("origem"));
        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Exemplo de dados
        viagens.addAll(
            new ViagemAtiva("2024-06-05", "João Silva", 123, "Lisboa", "Porto", "Pendente"),
            new ViagemAtiva("2024-06-06", "Maria Costa", 456, "Porto", "Coimbra", "Aceite"),
            new ViagemAtiva("2024-06-07", "Carlos Dias", 789, "Faro", "Lisboa", "Recusada")
        );
        viagensTable.setItems(viagens);
        atualizarInfoLabel();

        searchButton.setOnAction(e -> pesquisar());
        searchField.setOnKeyReleased(this::pesquisarAoDigitar);

        // Feedback label (inicialmente invisível)
        if (feedbackLabel != null) {
            feedbackLabel.setVisible(false);
        }

        // Coluna Aceitar
        aceitarColumn.setCellFactory(col -> new TableCell<ViagemAtiva, Void>() {
            private final Button btn = new Button("Aceitar");
            {
                btn.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-background-radius: 8;");
                btn.setTooltip(new Tooltip("Aceitar esta viagem"));
                btn.setOnAction(e -> {
                    ViagemAtiva viagem = getTableView().getItems().get(getIndex());
                    if (!viagem.getStatus().equalsIgnoreCase("Aceite")) {
                        viagem.setStatus("Aceite");
                        btn.setDisable(true);
                        TableCell<ViagemAtiva, Void> cell = this;
                        TableRow<ViagemAtiva> row = cell.getTableRow();
                        TableCell<ViagemAtiva, Void> recusarCell = (TableCell<ViagemAtiva, Void>) row.getChildrenUnmodifiable().get(6);
                        recusarCell.setDisable(true);
                        mostrarFeedback("Viagem aceite com sucesso!", true);
                        viagensTable.refresh();
                        // Aqui podes chamar o backend para atualizar o estado
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ViagemAtiva viagem = getTableView().getItems().get(getIndex());
                    if (viagem.getStatus().equalsIgnoreCase("Aceite")) {
                        btn.setDisable(true);
                        btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 8;");
                    } else if (viagem.getStatus().equalsIgnoreCase("Recusada")) {
                        btn.setDisable(true);
                        btn.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-background-radius: 8;");
                    } else {
                        btn.setDisable(false);
                        btn.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-background-radius: 8;");
                    }
                    setGraphic(btn);
                }
            }
        });

        // Coluna Recusar
        recusarColumn.setCellFactory(col -> new TableCell<ViagemAtiva, Void>() {
            private final Button btn = new Button("Recusar");
            {
                btn.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-background-radius: 8;");
                btn.setTooltip(new Tooltip("Recusar esta viagem"));
                btn.setOnAction(e -> {
                    ViagemAtiva viagem = getTableView().getItems().get(getIndex());
                    if (!viagem.getStatus().equalsIgnoreCase("Recusada")) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmar recusa");
                        alert.setHeaderText(null);
                        alert.setContentText("Tem a certeza que pretende recusar esta viagem?");
                        alert.showAndWait().ifPresent(result -> {
                            if (result.getText().equalsIgnoreCase("OK")) {
                                viagem.setStatus("Recusada");
                                btn.setDisable(true);
                                TableCell<ViagemAtiva, Void> cell = this;
                                TableRow<ViagemAtiva> row = cell.getTableRow();
                                TableCell<ViagemAtiva, Void> aceitarCell = (TableCell<ViagemAtiva, Void>) row.getChildrenUnmodifiable().get(5);
                                aceitarCell.setDisable(true);
                                mostrarFeedback("Viagem recusada!", false);
                                viagensTable.refresh();
                                // Aqui podes chamar o backend para atualizar o estado
                            }
                        });
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ViagemAtiva viagem = getTableView().getItems().get(getIndex());
                    if (viagem.getStatus().equalsIgnoreCase("Recusada")) {
                        btn.setDisable(true);
                        btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 8;");
                    } else if (viagem.getStatus().equalsIgnoreCase("Aceite")) {
                        btn.setDisable(true);
                        btn.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-background-radius: 8;");
                    } else {
                        btn.setDisable(false);
                        btn.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-background-radius: 8;");
                    }
                    setGraphic(btn);
                }
            }
        });

        // Cores no status
        statusColumn.setCellFactory(column -> new TableCell<ViagemAtiva, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    switch (status.toLowerCase()) {
                        case "aceite":
                            setStyle("-fx-background-color: #e6f9ea; -fx-text-fill: #219653; -fx-font-weight: bold;");
                            break;
                        case "recusada":
                            setStyle("-fx-background-color: #fdeaea; -fx-text-fill: #d32f2f; -fx-font-weight: bold;");
                            break;
                        case "pendente":
                            setStyle("-fx-background-color: #fffbe6; -fx-text-fill: #f2c200; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });

        // Detalhes da viagem ao clicar na linha
        viagensTable.setRowFactory(tv -> {
            TableRow<ViagemAtiva> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    ViagemAtiva viagem = row.getItem();
                    mostrarDetalhesViagem(viagem);
                }
            });
            return row;
        });

        if (estadoFilterCombo != null) {
            estadoFilterCombo.setItems(FXCollections.observableArrayList("Todas", "Aceite", "Recusada", "Pendente"));
            estadoFilterCombo.getSelectionModel().selectFirst();
            estadoFilterCombo.setOnAction(e -> pesquisar());
        }
    }

    private void pesquisarAoDigitar(KeyEvent event) {
        pesquisar();
    }

    private void pesquisar() {
        String termo = searchField.getText().toLowerCase();
        String estadoSelecionado = estadoFilterCombo != null ? estadoFilterCombo.getValue() : "Todas";
        if ((termo.isEmpty() || termo.isBlank()) && (estadoSelecionado == null || estadoSelecionado.equals("Todas"))) {
            viagensTable.setItems(viagens);
        } else {
            ObservableList<ViagemAtiva> filtradas = viagens.filtered(v -> {
                boolean correspondePesquisa = termo.isEmpty() ||
                        v.getData().toLowerCase().contains(termo) ||
                        v.getCliente().toLowerCase().contains(termo) ||
                        v.getOrigem().toLowerCase().contains(termo) ||
                        v.getDestino().toLowerCase().contains(termo) ||
                        v.getStatus().toLowerCase().contains(termo);
                boolean correspondeEstado = estadoSelecionado == null || estadoSelecionado.equals("Todas") ||
                        v.getStatus().equalsIgnoreCase(estadoSelecionado);
                return correspondePesquisa && correspondeEstado;
            });
            viagensTable.setItems(filtradas);
        }
        atualizarInfoLabel();
    }

    private void atualizarInfoLabel() {
        infoLabel.setText("Total de viagens: " + viagensTable.getItems().size());
    }

    private void mostrarFeedback(String mensagem, boolean sucesso) {
        if (feedbackLabel != null) {
            feedbackLabel.setText(mensagem);
            feedbackLabel.setStyle(sucesso ? "-fx-text-fill: #219653; -fx-font-weight: bold;" : "-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
            feedbackLabel.setVisible(true);
            new Thread(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(() -> feedbackLabel.setVisible(false));
            }).start();
        }
    }

    private void mostrarDetalhesViagem(ViagemAtiva viagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Detalhes da Viagem");
        alert.setHeaderText("Informação da Viagem");
        alert.setContentText(
                "Data: " + viagem.getData() + "\n" +
                "Cliente: " + viagem.getCliente() + "\n" +
                "Origem: " + viagem.getOrigem() + "\n" +
                "Destino: " + viagem.getDestino() + "\n" +
                "Estado: " + viagem.getStatus()
        );
        alert.showAndWait();
    }

    // Modelo interno para exemplo
    public static class ViagemAtiva {
        private final String data;
        private final String clienteNome;
        private final int clienteId;
        private final String origem;
        private final String destino;
        private String status;

        public ViagemAtiva(String data, String clienteNome, int clienteId, String origem, String destino, String status) {
            this.data = data;
            this.clienteNome = clienteNome;
            this.clienteId = clienteId;
            this.origem = origem;
            this.destino = destino;
            this.status = status;
        }
        public String getData() { return data; }
        public String getCliente() { return clienteNome + " (ID: " + clienteId + ")"; }
        public String getOrigem() { return origem; }
        public String getDestino() { return destino; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
