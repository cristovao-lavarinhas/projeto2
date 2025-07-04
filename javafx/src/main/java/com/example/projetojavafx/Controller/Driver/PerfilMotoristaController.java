package com.example.projetojavafx.Controller.Driver;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.layout.VBox;

public class PerfilMotoristaController {
    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private TextField moradaField;
    @FXML private TextField licencaField;
    @FXML private TextField ibanField;
    @FXML private Button guardarButton;
    @FXML private Label feedbackLabel;
    @FXML private Label estadoLabel;
    @FXML private Label ultimoLoginLabel;
    @FXML private Button alterarPasswordButton;

    @FXML
    public void initialize() {
        feedbackLabel.setVisible(false);
        // Mock de dados
        nomeField.setText("João Silva");
        emailField.setText("joao.silva@email.com");
        telefoneField.setText("912345678");
        moradaField.setText("Rua das Flores, 123, Lisboa");
        licencaField.setText("MTR-2024-001");
        ibanField.setText("PT50000201231234567890154");
        licencaField.setEditable(false);
        guardarButton.setOnAction(e -> guardarPerfil());
        estadoLabel.setText("Ativo");
        ultimoLoginLabel.setText("2024-06-10 14:32");
        estadoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #219653;");
        alterarPasswordButton.setOnAction(e -> mostrarDialogoAlterarPassword());
    }

    private void guardarPerfil() {
        // Aqui podes guardar os dados no backend
        mostrarFeedback("Perfil guardado com sucesso! (mock)", true);
    }

    private void mostrarFeedback(String mensagem, boolean sucesso) {
        feedbackLabel.setText(mensagem);
        feedbackLabel.setStyle(sucesso ? "-fx-text-fill: #219653; -fx-font-weight: bold;" : "-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        feedbackLabel.setVisible(true);
        new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(() -> feedbackLabel.setVisible(false));
        }).start();
    }

    private void mostrarDialogoAlterarPassword() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Alterar Palavra-passe");
        dialog.setHeaderText("Insira a nova palavra-passe");
        PasswordField novaPass = new PasswordField();
        novaPass.setPromptText("Nova palavra-passe");
        PasswordField confirmarPass = new PasswordField();
        confirmarPass.setPromptText("Confirmar palavra-passe");
        VBox vbox = new VBox(10, new Label("Nova palavra-passe:"), novaPass, new Label("Confirmar palavra-passe:"), confirmarPass);
        dialog.getDialogPane().setContent(vbox);
        ButtonType okButtonType = new ButtonType("Alterar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return novaPass.getText();
            }
            return null;
        });
        dialog.showAndWait().ifPresent(pass -> {
            if (!novaPass.getText().isEmpty() && novaPass.getText().equals(confirmarPass.getText())) {
                mostrarFeedback("Palavra-passe alterada com sucesso! (mock)", true);
            } else {
                mostrarFeedback("As palavras-passe não coincidem!", false);
            }
        });
    }
} 