package com.example.projetojavafx.Controller.Driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ContatarSuporteController {
    @FXML private ListView<Conversa> conversaListView;
    @FXML private VBox chatBox;
    @FXML private TextField txtMensagem;
    @FXML private Button btnEnviar;
    @FXML private Label lblNomeCliente;
    @FXML private Label lblData;
    @FXML private ScrollPane chatScrollPane;
    @FXML private ImageView imgAvatar;

    private final List<Conversa> conversas = new ArrayList<>();
    private Conversa conversaSelecionada;

    @FXML
    public void initialize() {
        mockConversas();
        conversaListView.getItems().addAll(conversas);
        conversaListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Conversa item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    HBox h = new HBox(8);
                    ImageView avatar = new ImageView(item.avatarUrl);
                    avatar.setFitHeight(28);
                    avatar.setFitWidth(28);
                    Label nome = new Label(item.nomeCliente);
                    nome.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
                    Label preview = new Label(item.getUltimaMensagemPreview());
                    preview.setStyle("-fx-text-fill: #888; -fx-font-size: 11px;");
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    Label badge = new Label(item.getNaoLidas() > 0 ? String.valueOf(item.getNaoLidas()) : "");
                    badge.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 2 7; -fx-background-radius: 10;" + (item.getNaoLidas() > 0 ? "" : "-fx-opacity: 0;"));
                    h.getChildren().addAll(avatar, nome, spacer, badge);
                    setGraphic(h);
                    setText(null);
                    setStyle(item.getNaoLidas() > 0 ? "-fx-background-color: #eaf6ff;" : "");
                }
            }
        });
        conversaListView.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) mostrarConversa(sel);
        });
        if (!conversas.isEmpty()) {
            conversaListView.getSelectionModel().select(0);
        }
        btnEnviar.setOnAction(e -> enviarMensagem());
    }

    private void mockConversas() {
        Conversa c1 = new Conversa("João Silva", "/com/example/projetojavafx/icons/user.png");
        c1.mensagens.add(new Mensagem("cliente", "Olá, tenho uma dúvida sobre a viagem.", LocalDateTime.now().minusHours(2), false));
        c1.mensagens.add(new Mensagem("motorista", "Claro, diga!", LocalDateTime.now().minusHours(2).plusMinutes(5), true));
        c1.mensagens.add(new Mensagem("cliente", "O valor está correto?", LocalDateTime.now().minusHours(1), false));
        Conversa c2 = new Conversa("Maria Costa", "/com/example/projetojavafx/icons/user.png");
        c2.mensagens.add(new Mensagem("cliente", "Bom dia, perdi um objeto no carro.", LocalDateTime.now().minusDays(1), false));
        c2.mensagens.add(new Mensagem("motorista", "Vou verificar e aviso.", LocalDateTime.now().minusDays(1).plusMinutes(10), true));
        conversas.add(c1);
        conversas.add(c2);
    }

    private void mostrarConversa(Conversa c) {
        conversaSelecionada = c;
        lblNomeCliente.setText(c.nomeCliente);
        try {
            imgAvatar.setImage(new Image(c.avatarUrl, true));
        } catch (Exception e) {
            imgAvatar.setImage(new Image(getClass().getResourceAsStream("/com/example/projetojavafx/icons/user.png")));
        }
        lblData.setText(c.getUltimaMensagemData());
        chatBox.getChildren().clear();
        for (Mensagem m : c.mensagens) {
            HBox msgHBox = new HBox();
            msgHBox.setSpacing(8);
            msgHBox.setFillHeight(true);
            Label bubble = new Label(m.texto);
            bubble.setWrapText(true);
            bubble.setMaxWidth(260);
            bubble.setStyle("-fx-padding: 8 14; -fx-background-radius: 16; -fx-font-size: 13px;" +
                (m.autor.equals("motorista") ? "-fx-background-color: #3498db; -fx-text-fill: white; -fx-alignment: CENTER_RIGHT;" : "-fx-background-color: #f1f1f1; -fx-text-fill: #233a80; -fx-alignment: CENTER_LEFT;"));
            Region spacer = new Region();
            if (m.autor.equals("motorista")) {
                msgHBox.getChildren().addAll(spacer, bubble);
            } else {
                msgHBox.getChildren().addAll(bubble, spacer);
            }
            HBox.setHgrow(spacer, Priority.ALWAYS);
            chatBox.getChildren().add(msgHBox);
            m.lida = true;
        }
        chatScrollPane.setVvalue(1.0);
    }

    private void enviarMensagem() {
        String texto = txtMensagem.getText().trim();
        if (texto.isEmpty() || conversaSelecionada == null) return;
        conversaSelecionada.mensagens.add(new Mensagem("motorista", texto, LocalDateTime.now(), true));
        txtMensagem.clear();
        mostrarConversa(conversaSelecionada);
    }

    // Classes internas
    private static class Conversa {
        String nomeCliente;
        String avatarUrl;
        List<Mensagem> mensagens = new ArrayList<>();
        Conversa(String nomeCliente, String avatarUrl) {
            this.nomeCliente = nomeCliente;
            this.avatarUrl = avatarUrl;
        }
        int getNaoLidas() {
            int n = 0;
            for (Mensagem m : mensagens) if (!m.lida && m.autor.equals("cliente")) n++;
            return n;
        }
        String getUltimaMensagemPreview() {
            if (mensagens.isEmpty()) return "";
            return mensagens.get(mensagens.size()-1).texto;
        }
        String getUltimaMensagemData() {
            if (mensagens.isEmpty()) return "";
            return mensagens.get(mensagens.size()-1).data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
    }
    private static class Mensagem {
        String autor;
        String texto;
        LocalDateTime data;
        boolean lida;
        Mensagem(String autor, String texto, LocalDateTime data, boolean lida) {
            this.autor = autor;
            this.texto = texto;
            this.data = data;
            this.lida = lida;
        }
    }
} 