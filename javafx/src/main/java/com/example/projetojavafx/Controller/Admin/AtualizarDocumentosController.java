package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Motorista;
import com.example.projetojavafx.Service.MotoristaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AtualizarDocumentosController {

    @FXML
    private ComboBox<Motorista> motoristaComboBox;

    @FXML
    private void initialize() {
        motoristaComboBox.setItems(MotoristaService.listarMotoristas());

        motoristaComboBox.setOnAction(event -> {
            Motorista motorista = motoristaComboBox.getValue();
            if (motorista != null) {
                verificarDocumentosFaltando(motorista);
            }
        });
    }

    @FXML
    private void uploadLicenca() {
        selecionarArquivo("Licença TVDE");
    }

    @FXML
    private void uploadSeguro() {
        selecionarArquivo("Seguro de Viagem");
    }

    @FXML
    private void uploadDocumentoVeiculo() {
        selecionarArquivo("Documento do Veículo");
    }

    private void selecionarArquivo(String tipoDocumento) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar " + tipoDocumento);

        if (motoristaComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Motorista Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecione um motorista antes de fazer upload.");
            alert.showAndWait();
            return;
        }

        fileChooser.showOpenDialog(new Stage());

        // Simula marcar documento como entregue
        if (tipoDocumento.equals("Licença TVDE")) {
            motoristaComboBox.getValue().getDocumentos().setLicencaTVDE(true);
        } else if (tipoDocumento.equals("Seguro de Viagem")) {
            motoristaComboBox.getValue().getDocumentos().setSeguroViagem(true);
        } else if (tipoDocumento.equals("Documento do Veículo")) {
            motoristaComboBox.getValue().getDocumentos().setDocumentoVeiculo(true);
        }
    }

    @FXML
    private void guardarAlteracoes() {
        if (motoristaComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Motorista Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecione um motorista para guardar alterações.");
            alert.showAndWait();
            return;
        }

        // Simulamos que as alterações foram guardadas
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Documentos atualizados com sucesso para " + motoristaComboBox.getValue().getNome() + "!");
        alert.showAndWait();
    }


    private void verificarDocumentosFaltando(Motorista motorista) {
        StringBuilder mensagem = new StringBuilder();

        if (!motorista.getDocumentos().isLicencaTVDE()) {
            mensagem.append("- Licença TVDE em falta\n");
        }
        if (!motorista.getDocumentos().isSeguroViagem()) {
            mensagem.append("- Seguro de Viagem em falta\n");
        }
        if (!motorista.getDocumentos().isDocumentoVeiculo()) {
            mensagem.append("- Documento do Veículo em falta\n");
        }

        if (!mensagem.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Documentos em Falta");
            alert.setHeaderText("Documentos pendentes para " + motorista.getNome());
            alert.setContentText(mensagem.toString());
            alert.showAndWait();
        }
    }

}
