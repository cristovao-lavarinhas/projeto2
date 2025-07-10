package com.example.projetojavafx.Controller.Admin;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML private StackPane contentArea;
    @FXML private Label greetingLabel;
    @FXML private TextField searchField;
    @FXML private ImageView logoImageView;

    @FXML
    public void initialize() {
        greetingLabel.setText("Bem-vindo, Admin!");
        configurarLogo();
        mostrarResumoDashboard();
    }

    private void configurarLogo() {
        try {
            javafx.scene.image.Image logo = new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/projetojavafx/icons/icon.png"));
            logoImageView.setImage(logo);
            logoImageView.setFitHeight(38);
            logoImageView.setFitWidth(80);
            logoImageView.setPreserveRatio(true);
            logoImageView.setStyle("-fx-cursor: hand; -fx-padding: 0 16 0 8;");
            logoImageView.setOnMouseClicked(e -> mostrarResumoDashboard());
        } catch (Exception e) {
            System.err.println("Erro ao carregar o logo: " + e.getMessage());
        }
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node page = loader.load();
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarResumoDashboard() {
        VBox resumoBox = new VBox(30);
        resumoBox.setPadding(new Insets(40));
        resumoBox.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Resumo Geral do Sistema");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox cards = new HBox(30);
        cards.setAlignment(Pos.CENTER);

        cards.getChildren().addAll(
                criarCard("Motoristas Ativos", ""),
                criarCard("Viaturas Registadas", ""),
                criarCard("Pedidos Pendentes", ""),
                criarCard("Viagens Concluídas", "")
        );

        Label ultimasLabel = new Label("Últimas Atividades");
        ultimasLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> atividades = new ListView<>();
        // Nenhuma atividade por default
        atividades.setMaxHeight(150);

        resumoBox.getChildren().addAll(titulo, cards, ultimasLabel, atividades);
        contentArea.getChildren().setAll(resumoBox);
    }

    private VBox criarCard(String titulo, String valor) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(180, 100);

        // Estilo visual
        card.setStyle(
                "-fx-background-color: #f4f4f4;" +
                        "-fx-padding: 20;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #ddd;" +
                        "-fx-border-radius: 12;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 4, 0, 0, 1);"
        );

        // Título
        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #555;" +
                        "-fx-font-weight: normal;"
        );

        // Valor
        Label valorLabel = new Label(valor);
        valorLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-text-fill: #233a80;" +
                        "-fx-font-weight: bold;"
        );

        card.getChildren().addAll(tituloLabel, valorLabel);
        return card;
    }

    @FXML
    private void showNotifications() {
        // Simples: mostra um alerta informativo
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Notificações");
        alert.setHeaderText(null);
        alert.setContentText("Nenhuma notificação disponível.");
        alert.showAndWait();
    }


    // Botões
    @FXML private void listarMotorista() { loadPage("/com/example/projetojavafx/Admin/ListarMotoristas.fxml"); }
    @FXML private void pedidosInscricao() { loadPage("/com/example/projetojavafx/Admin/PedidosInscricao.fxml"); }
    @FXML private void estadoMotorista() { loadPage("/com/example/projetojavafx/Admin/EstadoMotorista.fxml"); }
    @FXML private void estado() { loadPage("/com/example/projetojavafx/Admin/VerificarEstado.fxml"); }
    @FXML private void registarViatura() { loadPage("/com/example/projetojavafx/Admin/RegistarViatura.fxml"); }
    @FXML private void listarViatura() { loadPage("/com/example/projetojavafx/Admin/ListarViatura.fxml"); }
    @FXML private void associarViatura() { loadPage("/com/example/projetojavafx/Admin/AssociarViatura.fxml"); }
    @FXML private void gerirDocumentos() { loadPage("/com/example/projetojavafx/Admin/GerirDocumentos.fxml"); }
    @FXML private void estadoViatura() { loadPage("/com/example/projetojavafx/Admin/EstadoViatura.fxml"); }
    @FXML private void historicoViagens() { loadPage("/com/example/projetojavafx/Admin/HistoricoViagens.fxml"); }
    @FXML private void faturacao() { loadPage("/com/example/projetojavafx/Admin/Faturacao.fxml"); }
    @FXML private void exportarRelatorios() { loadPage("/com/example/projetojavafx/Admin/ExportarRelatorios.fxml"); }
    @FXML private void suporte() { loadPage("/com/example/projetojavafx/Admin/Suporte.fxml"); }

    @FXML
    private void handleLogout(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new javafx.scene.Scene(root));
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.show();

        // Fechar a janela atual (dashboard admin)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
