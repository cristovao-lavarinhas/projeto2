package com.example.projetojavafx.Controller.Admin;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class ExportarRelatoriosController {

    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private ComboBox<String> motoristaCombo;

    @FXML
    public void initialize() {
        motoristaCombo.getItems().addAll("João Silva", "Ana Costa");
    }

    @FXML
    private void exportarPDF() {
        if (!validarCampos()) return;

        LocalDate inicio = dataInicioPicker.getValue();
        LocalDate fim = dataFimPicker.getValue();
        String motorista = motoristaCombo.getValue();

        try {
            String dest = "relatorio_pagamentos_" + motorista.replace(" ", "_") + ".pdf";

            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Carregar imagem do logo dos resources
            InputStream logoStream = getClass().getResourceAsStream("/com/example/projetojavafx/icons/logotipo.png");
            if (logoStream != null) {
                byte[] imageBytes = logoStream.readAllBytes();
                com.itextpdf.io.image.ImageData imageData = com.itextpdf.io.image.ImageDataFactory.create(imageBytes);
                com.itextpdf.layout.element.Image logo = new com.itextpdf.layout.element.Image(imageData)
                        .scaleToFit(120, 120)
                        .setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
                document.add(logo);
            }

            // Espaço após o logotipo
            document.add(new Paragraph("\n"));

            // Título e dados
            document.add(new Paragraph("Relatório de Pagamentos")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Motorista: " + motorista));
            document.add(new Paragraph("Intervalo: " + inicio + " a " + fim));
            document.add(new Paragraph("\n"));

            // Tabela
            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 4, 4}))
                    .useAllAvailableWidth();

            table.addHeaderCell("Data");
            table.addHeaderCell("Descrição");
            table.addHeaderCell("Valor (€)");

            List<String[]> pagamentos = List.of(
                    new String[]{"2025-06-01", "Entrega 1", "25.00"},
                    new String[]{"2025-06-03", "Entrega 2", "30.00"},
                    new String[]{"2025-06-06", "Extra serviço", "15.00"}
            );

            double total = 0.0;
            for (String[] row : pagamentos) {
                table.addCell(row[0]);
                table.addCell(row[1]);
                table.addCell(row[2]);
                total += Double.parseDouble(row[2]);
            }

            document.add(table);
            document.add(new Paragraph("\nTotal Recebido: " + String.format("%.2f€", total)));

            document.close();
            mostrarToast("Relatório PDF exportado com sucesso!");
            abrirFicheiro(new File(dest));

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao gerar o PDF: " + e.getMessage());
        }
    }



    @FXML
    private void exportarCSV() {
        if (!validarCampos()) return;

        LocalDate inicio = dataInicioPicker.getValue();
        LocalDate fim = dataFimPicker.getValue();
        String motorista = motoristaCombo.getValue();
        if (motorista == null) motorista = "Todos";

        String nomeFicheiro = "relatorio_pagamentos_" + motorista.replace(" ", "_") + ".csv";
        File ficheiro = new File(nomeFicheiro);

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(ficheiro), StandardCharsets.UTF_8))) {

            writer.println("Relatório de Pagamentos");
            writer.println("Motorista:;" + motorista);
            writer.println("Intervalo:;" + inicio + " a " + fim);
            writer.println();

            writer.println("Data;Descrição;Valor (€)");

            List<String[]> pagamentos = List.of(
                    new String[]{"2025-06-01", "Entrega 1", "25.00"},
                    new String[]{"2025-06-03", "Entrega 2", "30.00"},
                    new String[]{"2025-06-06", "Extra serviço", "15.00"}
            );

            double total = 0.0;
            for (String[] linha : pagamentos) {
                writer.println(String.join(";", linha));
                total += Double.parseDouble(linha[2]);
            }

            writer.println();
            writer.println(";;Total");
            writer.println(";;" + String.format("%.2f", total) + "€");

            mostrarToast("Relatório CSV exportado com sucesso!");
            abrirFicheiro(ficheiro);

        } catch (IOException e) {
            mostrarAlerta("Erro", "Erro ao exportar CSV: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (dataInicioPicker.getValue() == null || dataFimPicker.getValue() == null) {
            mostrarAlerta("Preenchimento obrigatório", "Por favor preenche ambas as datas.");
            return false;
        }

        if (dataInicioPicker.getValue().isAfter(dataFimPicker.getValue())) {
            mostrarAlerta("Datas inválidas", "A data inicial não pode ser posterior à data final.");
            return false;
        }

        return true;
    }

    private void abrirFicheiro(File file) {
        if (!file.exists()) return;

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            mostrarAlerta("Erro", "Não foi possível abrir o ficheiro: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarToast(String mensagem) {
        Platform.runLater(() -> {
            Label toastLabel = new Label(mensagem);
            toastLabel.setStyle("-fx-background-color: rgba(76, 175, 80, 0.9); -fx-text-fill: white; -fx-padding: 12px 24px; -fx-background-radius: 10;");
            toastLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            StackPane toastContent = new StackPane(toastLabel);
            Popup popup = new Popup();
            popup.getContent().add(toastContent);
            popup.setAutoFix(true);
            popup.setAutoHide(true);
            popup.setHideOnEscape(true);

            double x = dataInicioPicker.getScene().getWindow().getX();
            double y = dataInicioPicker.getScene().getWindow().getY();
            double width = dataInicioPicker.getScene().getWindow().getWidth();
            double height = dataInicioPicker.getScene().getWindow().getHeight();

            popup.show(dataInicioPicker.getScene().getWindow(), x + width / 2 - 100, y + height - 100);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> popup.hide());
            delay.play();
        });
    }
}
