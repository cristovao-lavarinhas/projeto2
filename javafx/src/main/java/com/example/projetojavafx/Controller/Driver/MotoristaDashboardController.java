package com.example.projetojavafx.Controller.Driver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

public class MotoristaDashboardController {

    @FXML private StackPane contentArea;
    @FXML private Label greetingLabel;
    @FXML private TextField searchField;
    @FXML private ImageView logoImageView;
    @FXML private StackPane notificationButtonPane;
    @FXML private Button notificationButton;
    @FXML private Label notificationBadge;
    @FXML private HBox statusIndicator;
    @FXML private Label statusLabel;

    private VBox notificationPopup;
    private List<Notificacao> notificacoes = new ArrayList<>();
    private Popup notificationPopupWindow;
    private boolean motoristaDisponivel = false; // Estado inicial: indisponível

    @FXML
    public void initialize() {
        greetingLabel.setText("Bem-vindo, Motorista!");
        configurarLogo();
        configurarStatusInicial();
        mostrarResumoDashboard();
        mockNotificacoes();
        atualizarBadge();
        setupNotificationPopup();
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

    private void configurarStatusInicial() {
        atualizarIndicadorStatus();
    }

    private void atualizarIndicadorStatus() {
        if (motoristaDisponivel) {
            statusLabel.setText("✅ DISPONÍVEL");
            statusLabel.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold; -fx-font-size: 14px;");
        } else {
            statusLabel.setText("🚫 INDISPONÍVEL");
            statusLabel.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold; -fx-font-size: 14px;");
        }
    }

    @FXML
    private void alternarStatusRapido() {
        motoristaDisponivel = !motoristaDisponivel;
        atualizarIndicadorStatus();
        
        if (motoristaDisponivel) {
            adicionarNotificacao("Status Atualizado", "Você está agora DISPONÍVEL para receber viagens!");
        } else {
            adicionarNotificacao("Status Atualizado", "Você está agora INDISPONÍVEL para receber viagens.");
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

        Label titulo = new Label("Resumo Geral do Motorista");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Seção de Status do Motorista
        VBox statusSection = criarSecaoStatus();
        
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

        resumoBox.getChildren().addAll(titulo, statusSection, cards, ultimasLabel, atividades);
        contentArea.getChildren().setAll(resumoBox);
    }

    private VBox criarSecaoStatus() {
        VBox statusSection = new VBox(15);
        statusSection.setAlignment(Pos.CENTER);
        statusSection.setPadding(new Insets(20));
        statusSection.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #ddd; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 4, 0, 0, 1);");

        Label statusTitulo = new Label("🏁 Status Atual");
        statusTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label statusDescricao = new Label("Selecione seu status atual para começar a receber viagens");
        statusDescricao.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-text-alignment: center;");

        HBox botoesStatus = new HBox(20);
        botoesStatus.setAlignment(Pos.CENTER);

        Button btnDisponivel = new Button("✅ DISPONÍVEL");
        btnDisponivel.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30; -fx-background-radius: 25; -fx-cursor: hand;");
        btnDisponivel.setOnAction(e -> definirStatusDisponivel());

        Button btnIndisponivel = new Button("⏸️ INDISPONÍVEL");
        btnIndisponivel.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30; -fx-background-radius: 25; -fx-cursor: hand;");
        btnIndisponivel.setOnAction(e -> definirStatusIndisponivel());

        botoesStatus.getChildren().addAll(btnDisponivel, btnIndisponivel);

        // Efeitos hover
        btnDisponivel.setOnMouseEntered(e -> btnDisponivel.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30; -fx-background-radius: 25; -fx-cursor: hand;"));
        btnDisponivel.setOnMouseExited(e -> btnDisponivel.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30; -fx-background-radius: 25; -fx-cursor: hand;"));

        btnIndisponivel.setOnMouseEntered(e -> btnIndisponivel.setStyle("-fx-background-color: #da190b; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30; -fx-background-radius: 25; -fx-cursor: hand;"));
        btnIndisponivel.setOnMouseExited(e -> btnIndisponivel.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30; -fx-background-radius: 25; -fx-cursor: hand;"));

        statusSection.getChildren().addAll(statusTitulo, statusDescricao, botoesStatus);
        return statusSection;
    }

    private void definirStatusDisponivel() {
        motoristaDisponivel = true;
        atualizarIndicadorStatus();
        adicionarNotificacao("Status Atualizado", "Você está agora DISPONÍVEL para receber viagens!");
        mostrarConfirmacaoStatus("✅ DISPONÍVEL", "Você está agora disponível para receber viagens!");
    }

    private void definirStatusIndisponivel() {
        motoristaDisponivel = false;
        atualizarIndicadorStatus();
        adicionarNotificacao("Status Atualizado", "Você está agora INDISPONÍVEL para receber viagens.");
        mostrarConfirmacaoStatus("🚫 INDISPONÍVEL", "Você está agora indisponível para receber viagens.");
    }

    private void mostrarConfirmacaoStatus(String status, String mensagem) {
        // Criar overlay de confirmação
        VBox overlay = new VBox(15);
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.8); -fx-background-radius: 15; -fx-padding: 30;");
        overlay.setMaxSize(400, 200);
        overlay.setMinSize(400, 200);

        Label statusLabel = new Label(status);
        statusLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label mensagemLabel = new Label(mensagem);
        mensagemLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-text-alignment: center;");

        Button btnOk = new Button("OK");
        btnOk.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btnOk.setOnAction(e -> {
            contentArea.getChildren().remove(overlay);
        });

        overlay.getChildren().addAll(statusLabel, mensagemLabel, btnOk);

        // Adicionar overlay ao contentArea
        contentArea.getChildren().add(overlay);
        
        // Remover automaticamente após 3 segundos
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> {
                    if (contentArea.getChildren().contains(overlay)) {
                        contentArea.getChildren().remove(overlay);
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
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

    private void mockNotificacoes() {
        notificacoes.clear();
        notificacoes.add(new Notificacao("Pagamento recebido", "Você recebeu 25,00€ pela viagem Porto.", LocalDateTime.now().minusHours(2), false));
        notificacoes.add(new Notificacao("Viagem aceite", "Sua viagem para Lisboa foi aceite.", LocalDateTime.now().minusDays(1), false));
        notificacoes.add(new Notificacao("Novo suporte", "Resposta do suporte disponível.", LocalDateTime.now().minusDays(2), true));
    }

    private void atualizarBadge() {
        long unread = notificacoes.stream().filter(n -> !n.lida).count();
        notificationBadge.setText(String.valueOf(unread));
        notificationBadge.setVisible(unread > 0);
    }

    @FXML
    private void showNotifications() {
        if (notificationPopupWindow != null && notificationPopupWindow.isShowing()) {
            notificationPopupWindow.hide();
            return;
        }
        if (notificationPopup == null) setupNotificationPopup();
        if (notificationPopupWindow == null) {
            notificationPopupWindow = new Popup();
            notificationPopupWindow.getContent().add(notificationPopup);
            notificationPopupWindow.setAutoHide(true);
        } else {
            notificationPopupWindow.getContent().setAll(notificationPopup);
        }
        Window window = notificationButton.getScene().getWindow();
        double x = window.getX() + notificationButton.localToScene(0, 0).getX() + notificationButton.getScene().getX();
        double y = window.getY() + notificationButton.localToScene(0, 0).getY() + notificationButton.getScene().getY() + notificationButton.getHeight() + 8;
        notificationPopupWindow.show(window, x, y);
    }

    private void setupNotificationPopup() {
        notificationPopup = new VBox();
        notificationPopup.getStyleClass().add("notification-popup");
        notificationPopup.setMaxWidth(370);
        notificationPopup.setMinWidth(340);
        notificationPopup.setPickOnBounds(true);

        // Seta (arrow)
        Region arrow = new Region();
        arrow.getStyleClass().add("notification-popup-arrow");
        notificationPopup.getChildren().add(arrow);

        // Título com ícone
        HBox titleBox = new HBox(8);
        Label icon = new Label("🔔");
        icon.setStyle("-fx-font-size: 17px; -fx-translate-y: 1;");
        Label title = new Label("Notificações");
        title.getStyleClass().add("notification-popup-title");
        titleBox.getChildren().addAll(icon, title);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setPadding(new Insets(0,0,0,10));
        notificationPopup.getChildren().add(titleBox);

        // Separador
        Region separator = new Region();
        separator.getStyleClass().add("notification-popup-separator");
        notificationPopup.getChildren().add(separator);

        VBox list = new VBox();
        if (notificacoes.isEmpty()) {
            Label empty = new Label("Sem notificações");
            empty.setStyle("-fx-text-fill: #aaa; -fx-padding: 30 0 30 0; -fx-font-size: 13px;");
            list.getChildren().add(empty);
        } else {
            for (Notificacao n : notificacoes) {
                VBox item = new VBox();
                item.getStyleClass().add("notification-item");
                if (!n.lida) item.getStyleClass().add("notification-item-unread");
                Label t = new Label(n.titulo);
                t.getStyleClass().add("notification-item-title");
                Label m = new Label(n.mensagem);
                m.getStyleClass().add("notification-item-message");
                Label d = new Label(n.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                d.getStyleClass().add("notification-item-date");
                item.getChildren().addAll(t, m, d);
                list.getChildren().add(item);
            }
        }
        ScrollPane scroll = new ScrollPane(list);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(200);
        scroll.setMaxHeight(220);
        scroll.setStyle("-fx-background-color:transparent;");
        notificationPopup.getChildren().add(scroll);

        HBox footer = new HBox();
        footer.getStyleClass().add("notification-popup-footer");
        Button markRead = new Button("Marcar todas como lidas");
        markRead.getStyleClass().add("notification-mark-read");
        markRead.setOnAction(e -> {
            notificacoes.forEach(n -> n.lida = true);
            atualizarBadge();
            setupNotificationPopup();
            if (notificationPopupWindow != null && notificationPopupWindow.isShowing()) {
                notificationPopupWindow.hide();
            }
        });
        footer.getChildren().add(markRead);
        notificationPopup.getChildren().add(footer);
    }

    // Método para adicionar notificações dinamicamente
    public void adicionarNotificacao(String titulo, String mensagem) {
        notificacoes.add(0, new Notificacao(titulo, mensagem, LocalDateTime.now(), false));
        atualizarBadge();
        setupNotificationPopup();
    }

    // Botões de navegação para o motorista
    @FXML private void historicoViagens() { loadPage("/com/example/projetojavafx/Driver/HistoricoViagens.fxml"); }
    @FXML private void viagensAtivas() { loadPage("/com/example/projetojavafx/Driver/ViagensAtivas.fxml"); }
    @FXML private void verPagamentos() { loadPage("/com/example/projetojavafx/Driver/VerPagamentos.fxml"); }
    @FXML private void contatarSuporte() { loadPage("/com/example/projetojavafx/Driver/ContatarSuporte.fxml"); }
    @FXML private void perfilMotorista() { loadPage("/com/example/projetojavafx/Driver/PerfilMotorista.fxml"); }
    @FXML private void minhaViatura() { loadPage("/com/example/projetojavafx/Driver/EstadoViatura.fxml"); }
    
    // Novas funcionalidades
    @FXML private void calendarioViagens() { loadPage("/com/example/projetojavafx/Driver/CalendarioViagens.fxml"); }
    @FXML private void verAvaliacoes() { loadPage("/com/example/projetojavafx/Driver/Avaliacoes.fxml"); }

    // Logout do motorista
    @FXML
    private void handleLogout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
        contentArea.getScene().setRoot(root);
    }

    // Classe interna para notificações
    private static class Notificacao {
        String titulo;
        String mensagem;
        LocalDateTime data;
        boolean lida;
        Notificacao(String titulo, String mensagem, LocalDateTime data, boolean lida) {
            this.titulo = titulo;
            this.mensagem = mensagem;
            this.data = data;
            this.lida = lida;
        }
    }
}
