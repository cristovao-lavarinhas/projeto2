package com.example.projetojavafx.Controller;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AdminDashboardController {

    @FXML private StackPane contentArea;
    @FXML private Label greetingLabel;
    @FXML private Label dateLabel;
    @FXML private TextField searchField;
    @FXML private ImageView logoImageView;

    @FXML
    public void initialize() {
        greetingLabel.setText("Bem-vindo, Admin!");
        dateLabel.setText("Hoje é " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

        Label titulo = new Label("Resumo Geral do Sistema");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox cards = new HBox(30);
        cards.setAlignment(Pos.CENTER);

        cards.getChildren().addAll(
                criarCard("Motoristas Ativos", "128"),
                criarCard("Viaturas Registadas", "56"),
                criarCard("Pedidos Pendentes", "7"),
                criarCard("Viagens Concluídas", "342")
        );

        Label ultimasLabel = new Label("Últimas Atividades");
        ultimasLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> atividades = new ListView<>();
        atividades.getItems().addAll(
                "✔ Motorista João aprovado",
                "✔ Viatura 32-AZ-87 registada",
                "✏ Documento atualizado por Maria",
                "📤 Pedido de inscrição de Carla recebido"
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



    // Botões
    @FXML private void listarMotorista() { loadPage("/com/example/projetojavafx/ListarMotoristas.fxml"); }
    @FXML private void pedidosInscricao() { loadPage("/com/example/projetojavafx/PedidosInscricao.fxml"); }
    @FXML private void estadoMotorista() { loadPage("/com/example/projetojavafx/EstadoMotorista.fxml"); }
    @FXML private void atualizarDocs() { loadPage("/com/example/projetojavafx/AtualizarDocumentos.fxml"); }
    @FXML private void estado() { loadPage("/com/example/projetojavafx/VerificarEstado.fxml"); }
    @FXML private void registarViatura() { loadPage("/com/example/projetojavafx/RegistarViatura.fxml"); }
    @FXML private void listarViatura() { loadPage("/com/example/projetojavafx/ListarViatura.fxml"); }
    @FXML private void associarViatura() { loadPage("/com/example/projetojavafx/AssociarViatura.fxml"); }
    @FXML private void gerirDocumentos() { loadPage("/com/example/projetojavafx/GerirDocumentos.fxml"); }
    @FXML private void estadoViatura() { loadPage("/com/example/projetojavafx/EstadoViatura.fxml"); }
    @FXML private void historicoViagens() { loadPage("/com/example/projetojavafx/HistoricoViagens.fxml"); }
    @FXML private void faturacao() { loadPage("/com/example/projetojavafx/Faturacao.fxml"); }
    @FXML private void controlarSaldo() { loadPage("/com/example/projetojavafx/ControlarSaldo.fxml"); }
    @FXML private void exportarRelatorios() { loadPage("/com/example/projetojavafx/ExportarRelatorios.fxml"); }
    @FXML private void suporte() { loadPage("/com/example/projetojavafx/Suporte.fxml"); }

    @FXML
    private void handleLogout() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/projetojavafx/Login.fxml")));
        contentArea.getScene().setRoot(root);
    }
}
