package com.example.projetojavafx.Controller.Driver;

import com.example.projetojavafx.Controller.SessaoUtilizador;
import com.example.projetojavafx.Modelo.Motorista;
import com.example.projetojavafx.Service.ApiClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    private Long motoristaId;
    private Motorista motoristaAtual;

    @FXML
    public void initialize() {
        feedbackLabel.setVisible(false);
        motoristaId = SessaoUtilizador.getMotoristaId();
        if (motoristaId != null) {
            ApiClient.get("/motoristas/" + motoristaId, Motorista.class)
                .thenAccept(motorista -> {
                    if (motorista != null) {
                        motoristaAtual = motorista;
                        javafx.application.Platform.runLater(() -> preencherCampos(motorista));
                    }
                });
        }
        guardarButton.setOnAction(e -> guardarPerfil());
        alterarPasswordButton.setOnAction(e -> mostrarDialogoAlterarPassword());
    }

    private void preencherCampos(Motorista motorista) {
        nomeField.setText(motorista.getNome());
        emailField.setText(motorista.getEmail());
        telefoneField.setText(motorista.getTelefone());
        moradaField.setText(motorista.getMorada());
        licencaField.setText(motorista.getLicenca());
        ibanField.setText(motorista.getIban());
        estadoLabel.setText(motorista.getEstado());
    }

    private void guardarPerfil() {
        if (motoristaAtual == null) return;
        motoristaAtual.setNome(nomeField.getText());
        motoristaAtual.setEmail(emailField.getText());
        motoristaAtual.setTelefone(telefoneField.getText());
        motoristaAtual.setMorada(moradaField.getText());
        motoristaAtual.setLicenca(licencaField.getText());
        motoristaAtual.setIban(ibanField.getText());
        motoristaAtual.setEstado(estadoLabel.getText());
        ApiClient.put("/motoristas/" + motoristaAtual.getId(), motoristaAtual, Motorista.class)
            .thenAccept(resp -> {
                if (resp != null) {
                    javafx.application.Platform.runLater(() -> {
                        mostrarFeedback("Perfil guardado com sucesso!", true);
                        // Refresh dos dados reais
                        ApiClient.get("/motoristas/" + motoristaAtual.getId(), Motorista.class)
                            .thenAccept(motorista -> {
                                if (motorista != null) {
                                    motoristaAtual = motorista;
                                    javafx.application.Platform.runLater(() -> preencherCampos(motorista));
                                }
                            });
                    });
                } else {
                    javafx.application.Platform.runLater(() -> mostrarFeedback("Erro ao guardar alterações.", false));
                }
            });
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
                // Chamar endpoint para alterar password
                java.util.Map<String, String> payload = new java.util.HashMap<>();
                payload.put("idMotorista", String.valueOf(motoristaId));
                payload.put("novaPassword", novaPass.getText());
                ApiClient.postText("/usuarios/alterar-password", payload)
                    .thenAccept(resp -> {
                        javafx.application.Platform.runLater(() -> mostrarFeedback("Palavra-passe alterada com sucesso!", true));
                    });
            } else {
                mostrarFeedback("As palavras-passe não coincidem!", false);
            }
        });
    }
} 