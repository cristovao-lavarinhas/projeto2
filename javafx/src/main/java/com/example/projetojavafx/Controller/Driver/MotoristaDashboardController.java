package com.example.projetojavafx.Controller.Driver;

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

import java.io.IOException;

public class MotoristaDashboardController {

    @FXML private StackPane contentArea;
    @FXML private Label greetingLabel;
    @FXML private TextField searchField;
    @FXML private ImageView logoImageView;

    @FXML
    public void initialize() {
        greetingLabel.setText("Bem-vindo, Motorista!");
        mostrarResumoDashboard();
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

        Label titulo = new Label("Resumo Geral do Motorista");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox cards = new HBox(30);
        cards.setAlignment(Pos.CENTER);

        cards.getChildren().addAll(
                criarCard("Viagens Ativas", "5"),
                criarCard("Viagens Concluídas", "342"),
                criarCard("Pagamentos Pendentes", "7"),
                criarCard("Documentos Atualizados", "3")
        );

        Label ultimasLabel = new Label("Últimas Atividades");
        ultimasLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> atividades = new ListView<>();
        atividades.getItems().addAll(
                "✔ Viagem para Porto concluída",
                "✔ Documentos atualizados",
                "📤 Viagem a Lisboa agendada"
        );
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

    // Botões de navegação para o motorista
    @FXML private void historicoViagens() { loadPage("/com/example/projetojavafx/Driver/HistoricoViagens.fxml"); }
    @FXML private void viagensAtivas() { loadPage("/com/example/projetojavafx/Driver/ViagensAtivas.fxml"); }
    @FXML private void verPagamentos() { loadPage("/com/example/projetojavafx/Driver/VerPagamentos.fxml"); }
    @FXML private void extratoPagamento() { loadPage("/com/example/projetojavafx/Driver/ExtratoPagamento.fxml"); }
    @FXML private void contatarSuporte() { loadPage("/com/example/projetojavafx/Driver/ContatarSuporte.fxml"); }
    @FXML private void perfilMotorista() { loadPage("/com/example/projetojavafx/Driver/PerfilMotorista.fxml"); }

    // Logout do motorista
    @FXML
    private void handleLogout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
        contentArea.getScene().setRoot(root);
    }
}
