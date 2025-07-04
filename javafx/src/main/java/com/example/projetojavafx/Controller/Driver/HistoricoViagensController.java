package com.example.projetojavafx.Controller.Driver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class HistoricoViagensController {

    @FXML
    private TableView<Viagem> viagensTable;
    @FXML
    private TableColumn<Viagem, String> dataColumn;
    @FXML
    private TableColumn<Viagem, String> origemColumn;
    @FXML
    private TableColumn<Viagem, String> destinoColumn;
    @FXML
    private TableColumn<Viagem, String> statusColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Label infoLabel;
    @FXML
    private ComboBox<String> estadoFilterCombo;

    private ObservableList<Viagem> viagens = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        origemColumn.setCellValueFactory(new PropertyValueFactory<>("origem"));
        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Cell factory para colorir o status
        statusColumn.setCellFactory(column -> new TableCell<Viagem, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    switch (status.toLowerCase()) {
                        case "concluída":
                            setStyle("-fx-background-color: #e6f9ea; -fx-text-fill: #219653; -fx-font-weight: bold;");
                            break;
                        case "cancelada":
                            setStyle("-fx-background-color: #fdeaea; -fx-text-fill: #d32f2f; -fx-font-weight: bold;");
                            break;
                        case "em curso":
                            setStyle("-fx-background-color: #fffbe6; -fx-text-fill: #f2c200; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });

        // Exemplo de dados
        viagens.addAll(
            new Viagem("2024-06-01", "Lisboa", "Porto", "Concluída"),
            new Viagem("2024-06-02", "Porto", "Coimbra", "Cancelada"),
            new Viagem("2024-06-03", "Faro", "Lisboa", "Em curso")
        );
        viagensTable.setItems(viagens);
        atualizarInfoLabel();

        searchButton.setOnAction(e -> pesquisar());
        searchField.setOnKeyReleased(this::pesquisarAoDigitar);

        if (estadoFilterCombo != null) {
            estadoFilterCombo.setItems(FXCollections.observableArrayList("Todos", "Concluída", "Cancelada", "Em curso"));
            estadoFilterCombo.getSelectionModel().selectFirst(); // Seleciona "Todos" por defeito
            estadoFilterCombo.setOnAction(e -> pesquisar());
        }
    }

    private void pesquisarAoDigitar(KeyEvent event) {
        pesquisar();
    }

    private void pesquisar() {
        String termo = searchField.getText().toLowerCase();
        String estadoSelecionado = estadoFilterCombo != null ? estadoFilterCombo.getValue() : "Todos";
        if ((termo.isEmpty() || termo.isBlank()) && (estadoSelecionado == null || estadoSelecionado.equals("Todos"))) {
            viagensTable.setItems(viagens);
        } else {
            ObservableList<Viagem> filtradas = viagens.filtered(v -> {
                boolean correspondePesquisa = termo.isEmpty() ||
                        v.getData().toLowerCase().contains(termo) ||
                        v.getOrigem().toLowerCase().contains(termo) ||
                        v.getDestino().toLowerCase().contains(termo) ||
                        v.getStatus().toLowerCase().contains(termo);
                boolean correspondeEstado = estadoSelecionado == null || estadoSelecionado.equals("Todos") ||
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

    // Modelo interno para exemplo
    public static class Viagem {
        private final String data;
        private final String origem;
        private final String destino;
        private final String status;

        public Viagem(String data, String origem, String destino, String status) {
            this.data = data;
            this.origem = origem;
            this.destino = destino;
            this.status = status;
        }
        public String getData() { return data; }
        public String getOrigem() { return origem; }
        public String getDestino() { return destino; }
        public String getStatus() { return status; }
    }
}
