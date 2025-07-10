package com.example.projetojavafx.Controller.Driver;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import com.example.projetojavafx.Service.ApiClient;

public class EstadoViaturaController {
    @FXML private Label lblMarcaModelo;
    @FXML private Label lblMatricula;
    @FXML private Label lblAnoCor;
    @FXML private Label lblEstado;
    @FXML private TableView<Documento> tblDocumentos;
    @FXML private TableColumn<Documento, String> colNome;
    @FXML private TableColumn<Documento, String> colValidade;
    @FXML private TableColumn<Documento, String> colEstado;
    @FXML private TableColumn<Documento, Void> colAcao;
    @FXML private Button btnVerHistorico;
    @FXML private VBox notificacoesBox;

    private final List<Documento> documentos = new ArrayList<>();

    @FXML
    public void initialize() {
        mockViatura();
        mockDocumentos();
        tblDocumentos.getItems().setAll(documentos);
        colNome.setCellValueFactory(data -> data.getValue().nomeProperty());
        colValidade.setCellValueFactory(data -> data.getValue().validadeProperty());
        colEstado.setCellValueFactory(data -> data.getValue().estadoProperty());
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    if (estado.equals("Válido")) setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    else if (estado.equals("A Expirar")) setStyle("-fx-text-fill: #f39c12; -fx-font-weight: bold;");
                    else setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                }
            }
        });
        addAcaoButtons();
        btnVerHistorico.setOnAction(e -> mostrarHistorico());
        carregarNotificacoes();
    }

    private void mockViatura() {
        // imgViatura.setImage(new Image(getClass().getResourceAsStream("/com/example/projetojavafx/images/dash.jpg")));
        lblMarcaModelo.setText("Toyota Corolla");
        lblMatricula.setText("Matrícula: 12-AB-34");
        lblAnoCor.setText("Ano: 2021   Cor: Cinzento");
        lblEstado.setText("Disponível");
        lblEstado.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
    }

    private void mockDocumentos() {
        documentos.clear();
        documentos.add(new Documento("Seguro", "2025-05-10", "Válido"));
        documentos.add(new Documento("Inspeção", "2024-08-01", "A Expirar"));
        documentos.add(new Documento("Livrete", "-", "Em Falta"));
        documentos.add(new Documento("Registo de Propriedade", "2024-12-31", "Válido"));
    }

    private void addAcaoButtons() {
        colAcao.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Documento, Void> call(final TableColumn<Documento, Void> param) {
                return new TableCell<>() {
                    private final Button btnUpload = new Button("Submeter");
                    {
                        btnUpload.setOnAction(e -> {
                            Documento doc = getTableView().getItems().get(getIndex());
                            submeterDocumento(doc);
                        });
                        btnUpload.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 4 14;");
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Documento doc = getTableView().getItems().get(getIndex());
                            if (doc.estado.equals("Válido")) setGraphic(null);
                            else setGraphic(btnUpload);
                        }
                    }
                };
            }
        });
    }

    private void submeterDocumento(Documento doc) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar ficheiro para " + doc.nome.get());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF, Imagem", "*.pdf", "*.jpg", "*.jpeg", "*.png")
        );
        Window window = tblDocumentos.getScene().getWindow();
        var file = fileChooser.showOpenDialog(window);
        if (file != null) {
            doc.estado.set("Pendente");
            doc.validade.set("A Validar");
            tblDocumentos.refresh();
            // Simular notificação (mock)
            mostrarNotificacao("Documento submetido", "O documento '" + doc.nome.get() + "' foi submetido para validação.");
        }
    }

    private void mostrarNotificacao(String titulo, String mensagem) {
        // Mock: pode ser integrado com o sistema real de notificações
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarHistorico() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Histórico de Submissões");
        alert.setHeaderText(null);
        alert.setContentText("(Mock) Aqui será mostrado o histórico de submissões de documentos.");
        alert.showAndWait();
    }

    private void carregarNotificacoes() {
        notificacoesBox.getChildren().clear();
        Long motoristaId = 1L; // Substituir pelo id real do motorista autenticado
        ApiClient.getList("/api/notificacoes/motorista/" + motoristaId, Notificacao.class)
            .thenAccept(notificacoes -> {
                if (notificacoes != null && !notificacoes.isEmpty()) {
                    javafx.application.Platform.runLater(() -> {
                        for (Notificacao n : notificacoes) {
                            if (!n.lida && n.mensagem != null && !n.mensagem.isEmpty()) {
                                HBox box = new HBox();
                                box.setStyle("-fx-background-color: #fff3cd; -fx-padding: 8 12; -fx-background-radius: 8; -fx-border-color: #ffeeba; -fx-border-radius: 8;");
                                Text txt = new Text("\u26A0 " + n.mensagem);
                                txt.setStyle("-fx-fill: #856404; -fx-font-size: 13px;");
                                box.getChildren().add(txt);
                                notificacoesBox.getChildren().add(box);
                            }
                        }
                    });
                }
            });
    }

    // Classe Documento
    public static class Documento {
        private final SimpleStringProperty nome;
        private final SimpleStringProperty validade;
        private final SimpleStringProperty estado;
        public Documento(String nome, String validade, String estado) {
            this.nome = new SimpleStringProperty(nome);
            this.validade = new SimpleStringProperty(validade);
            this.estado = new SimpleStringProperty(estado);
        }
        public String getNome() { return nome.get(); }
        public SimpleStringProperty nomeProperty() { return nome; }
        public String getValidade() { return validade.get(); }
        public SimpleStringProperty validadeProperty() { return validade; }
        public String getEstado() { return estado.get(); }
        public SimpleStringProperty estadoProperty() { return estado; }
    }

    // Classe para mapear notificações do backend
    public static class Notificacao {
        public Long id;
        public String mensagem;
        public boolean lida;
    }
} 