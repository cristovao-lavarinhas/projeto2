package com.example.projetojavafx.Controller.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SuporteController {

    @FXML private ComboBox<String> comboTipoUtilizador;
    @FXML private ComboBox<String> comboEstado;
    @FXML private ListView<String> listaTickets;
    @FXML private TextArea campoMensagem;
    @FXML private Button botaoEnviar;

    private final ObservableList<String> mensagens = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        comboTipoUtilizador.setItems(FXCollections.observableArrayList("Cliente", "Motorista"));
        comboTipoUtilizador.setValue("Cliente");

        comboEstado.setItems(FXCollections.observableArrayList("Aberto", "Fechado"));
        comboEstado.setValue("Aberto");

        listaTickets.setItems(mensagens);
    }

    @FXML
    private void enviarMensagem() {
        String tipo = comboTipoUtilizador.getValue();
        String estado = comboEstado.getValue();
        String texto = campoMensagem.getText().trim();

        if (texto.isEmpty()) {
            mostrarAlerta("Mensagem vazia", "Por favor escreve algo antes de enviar.");
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String linha = "[" + timestamp + "] (" + tipo + " - " + estado + ") " + texto;
        mensagens.add(linha);

        // Resposta automática
        if (texto.toLowerCase().contains("problema") || texto.toLowerCase().contains("ajuda")) {
            mensagens.add("[AutoResposta] Obrigado pelo contacto. Iremos responder em breve.");
        }

        campoMensagem.clear();
    }

    @FXML
    private void gerarRespostaAutomatica() {
        mensagens.add("[AutoResposta] Esta é uma mensagem automática de exemplo.");
    }

    @FXML
    private void exportarCSV() {
        String nomeFicheiro = "historico_suporte.csv";

        try (FileWriter writer = new FileWriter(nomeFicheiro)) {
            writer.write("Histórico de Mensagens de Suporte\n\n");

            for (String msg : mensagens) {
                writer.write(msg.replace(",", ";") + "\n"); // evita quebra em campos CSV
            }

            mostrarInformacao("Exportação Concluída", "O histórico foi exportado para: " + nomeFicheiro);

        } catch (IOException e) {
            mostrarAlerta("Erro", "Erro ao exportar CSV: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void mostrarInformacao(String titulo, String mensagem) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(titulo);
        info.setHeaderText(null);
        info.setContentText(mensagem);
        info.showAndWait();
    }
}
