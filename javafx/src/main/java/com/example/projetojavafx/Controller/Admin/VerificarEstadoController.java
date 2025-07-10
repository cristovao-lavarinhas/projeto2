package com.example.projetojavafx.Controller.Admin;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.example.projetojavafx.Modelo.Motorista;
import com.example.projetojavafx.Service.MotoristaService;
import com.example.projetojavafx.Service.ApiClient;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VerificarEstadoController {

    @FXML
    private ComboBox<Motorista> motoristaComboBox;

    @FXML
    private Label estadoMotoristaLabel;

    @FXML
    private Label documentosStatusLabel;

    @FXML
    private Label faltasLabel;

    @FXML
    private Button notificarButton;

    @FXML
    private void initialize() {
        motoristaComboBox.setItems(MotoristaService.listarMotoristas());
    }

    @FXML
    private void handleVerificar() {
        Motorista motorista = motoristaComboBox.getValue();

        if (motorista == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção necessária");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecione um motorista.");
            alert.showAndWait();
            notificarButton.setDisable(true);
            return;
        }

        // Estado do Motorista
        estadoMotoristaLabel.setText("Estado do Motorista: " + motorista.getEstado());

        // Documentos
        boolean licencaOK = motorista.getDocumentos().isLicencaTVDE();
        boolean seguroOK = motorista.getDocumentos().isSeguroViagem();
        boolean docVeiculoOK = motorista.getDocumentos().isDocumentoVeiculo();

        if (licencaOK && seguroOK && docVeiculoOK) {
            documentosStatusLabel.setText("Estado dos Documentos: ✅ Todos os documentos entregues");
            faltasLabel.setText("");
            notificarButton.setDisable(true);
        } else {
            documentosStatusLabel.setText("Estado dos Documentos: ❌ Documentos pendentes");
            StringBuilder faltas = new StringBuilder();

            if (!licencaOK) faltas.append("- Licença TVDE\n");
            if (!seguroOK) faltas.append("- Seguro de Viagem\n");
            if (!docVeiculoOK) faltas.append("- Documento do Veículo\n");

            faltasLabel.setText("Faltam:\n" + faltas.toString());
            notificarButton.setDisable(false);
        }
    }


    @FXML
    private void handleExportarRelatorio() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Relatório CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ficheiros CSV", "*.csv"));
            fileChooser.setInitialFileName("relatorio_motoristas.csv");

            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                PrintWriter writer = new PrintWriter(new FileWriter(file));

                // Cabeçalho CSV
                writer.println("Nome,Estado,Documentos OK");

                for (Motorista motorista : MotoristaService.listarMotoristas()) {
                    boolean documentosOk = motorista.getDocumentos().isLicencaTVDE()
                            && motorista.getDocumentos().isSeguroViagem()
                            && motorista.getDocumentos().isDocumentoVeiculo();

                    writer.println(
                            motorista.getNome() + "," +
                                    motorista.getEstado() + "," +
                                    (documentosOk ? "Sim" : "Não")
                    );
                }

                writer.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Relatório exportado com sucesso!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao exportar relatório.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNotificar() {
        Motorista motorista = motoristaComboBox.getValue();
        if (motorista == null) return;
        // Chamada ao backend para criar notificação
        String endpoint = "/api/notificacoes/motorista/" + motorista.getId();
        ApiClient.post(endpoint, "documentos em falta", String.class)
            .thenAccept(result -> {
                javafx.application.Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notificação Enviada");
                    alert.setHeaderText(null);
                    alert.setContentText("Notificação persistente enviada ao motorista '" + motorista.getNome() + "'.");
                    alert.showAndWait();
                    notificarButton.setDisable(true);
                });
            });
    }
}
