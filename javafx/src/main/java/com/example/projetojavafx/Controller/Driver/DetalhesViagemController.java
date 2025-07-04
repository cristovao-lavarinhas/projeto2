package com.example.projetojavafx.Controller.Driver;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetalhesViagemController {
    @FXML private Label dataLabel;
    @FXML private Label horaLabel;
    @FXML private Label origemLabel;
    @FXML private Label destinoLabel;
    @FXML private Label clienteLabel;
    @FXML private Label valorLabel;
    @FXML private Label estadoLabel;
    @FXML private Label avaliacaoLabel;
    @FXML private Button exportarPdfButton;
    @FXML private Button fecharButton;
    @FXML private Button abrirMapsButton;

    private ViagemDetalhe viagem;

    @FXML
    public void initialize() {
        exportarPdfButton.setOnAction(e -> exportarPDF());
        fecharButton.setOnAction(e -> fechar());
        if (abrirMapsButton != null) {
            abrirMapsButton.setOnAction(e -> abrirNoGoogleMaps());
        }
    }

    public void setViagem(ViagemDetalhe viagem) {
        this.viagem = viagem;
        dataLabel.setText(viagem.getData());
        horaLabel.setText(viagem.getHora());
        origemLabel.setText(viagem.getOrigem());
        destinoLabel.setText(viagem.getDestino());
        clienteLabel.setText(viagem.getCliente());
        valorLabel.setText(viagem.getValor());
        estadoLabel.setText(viagem.getEstado());
        avaliacaoLabel.setText(viagem.getAvaliacao());
    }

    private void exportarPDF() {
        try {
            String fileName = "DetalhesViagem_" + viagem.getData() + ".pdf";
            File file = new File(System.getProperty("user.home"), fileName);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("Detalhes da Viagem"));
            document.add(new Paragraph("Data: " + viagem.getData()));
            document.add(new Paragraph("Hora: " + viagem.getHora()));
            document.add(new Paragraph("Origem: " + viagem.getOrigem()));
            document.add(new Paragraph("Destino: " + viagem.getDestino()));
            document.add(new Paragraph("Cliente: " + viagem.getCliente()));
            document.add(new Paragraph("Valor: " + viagem.getValor()));
            document.add(new Paragraph("Estado: " + viagem.getEstado()));
            document.add(new Paragraph("Avaliação: " + viagem.getAvaliacao()));
            document.close();
            java.awt.Desktop.getDesktop().open(file);
        } catch (Exception e) {
            // feedback de erro
        }
    }

    private void fechar() {
        Stage stage = (Stage) fecharButton.getScene().getWindow();
        stage.close();
    }

    private void abrirNoGoogleMaps() {
        try {
            String destino = destinoLabel.getText();
            String url = "https://www.google.com/maps/dir/?api=1&destination=" + java.net.URLEncoder.encode(destino, "UTF-8");
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            // feedback de erro
        }
    }

    // Classe mock para exemplo
    public static class ViagemDetalhe {
        private final String data, hora, origem, destino, cliente, valor, estado, avaliacao;
        public ViagemDetalhe(String data, String hora, String origem, String destino, String cliente, String valor, String estado, String avaliacao) {
            this.data = data; this.hora = hora; this.origem = origem; this.destino = destino;
            this.cliente = cliente; this.valor = valor; this.estado = estado; this.avaliacao = avaliacao;
        }
        public String getData() { return data; }
        public String getHora() { return hora; }
        public String getOrigem() { return origem; }
        public String getDestino() { return destino; }
        public String getCliente() { return cliente; }
        public String getValor() { return valor; }
        public String getEstado() { return estado; }
        public String getAvaliacao() { return avaliacao; }
    }
} 