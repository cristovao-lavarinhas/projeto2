package com.example.projetojavafx.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.example.projetojavafx.Controller.Driver.MotoristaDashboardController;
import com.example.projetojavafx.Service.ApiClient;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

    public ImageView logoImageView;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private VBox loginBox;
    @FXML private VBox twofaBox;
    @FXML private VBox recoveryBox;
    @FXML private TextField twofaCodeField;
    @FXML private TextField recoveryEmailField;
    @FXML private TextField recoveryCodeField;
    @FXML private PasswordField newPasswordField;
    @FXML private Label recoveryStep2Label;
    @FXML private CheckBox rememberMeCheckBox;
    @FXML private Button alterarPasswordButton;
    @FXML private Label loginTitleLabel;
    @FXML private Label loginSubtitleLabel;
    @FXML private Label loginDividerLabel;

    private String tempEmail; // Para guardar o email entre etapas

    // --- Transições ---
    private void showStep(VBox show, VBox... hide) {
        for (VBox box : hide) {
            if (box.isVisible()) {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(250), box);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(e -> box.setVisible(false));
                fadeOut.play();
            } else {
                box.setVisible(false);
                box.setOpacity(0.0);
            }
        }
        show.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), show);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void showLoginStep() {
        showStep(loginBox, twofaBox, recoveryBox);
        loginTitleLabel.setVisible(true);
        loginSubtitleLabel.setVisible(true);
        loginDividerLabel.setVisible(true);
    }

    @FXML
    private void show2FAStep() {
        showStep(twofaBox, loginBox, recoveryBox);
    }

    @FXML
    private void showRecoveryStep() {
        showStep(recoveryBox, loginBox, twofaBox);
        // Reset campos
        recoveryEmailField.setText("");
        recoveryCodeField.setText("");
        newPasswordField.setText("");
        recoveryStep2Label.setVisible(false);
        recoveryCodeField.setVisible(false);
        newPasswordField.setVisible(false);
        alterarPasswordButton.setVisible(false);
        // Ocultar labels do login
        loginTitleLabel.setVisible(false);
        loginSubtitleLabel.setVisible(false);
        loginDividerLabel.setVisible(false);
    }

    // --- Login inicial ---
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        tempEmail = email;
        if ("admin@admin.com".equals(email) && "admin".equals(password)) {
            abrirDashboardAdmin(event);
            return;
        }
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);
        CompletableFuture<String> future = ApiClient.postText("/auth/login", loginData);
        future.thenAccept(response -> {
            javafx.application.Platform.runLater(() -> {
                if (response != null && response.contains("Login realizado com sucesso")) {
                    String nome = extrairNomeDoJson(response);
                    Long idMotorista = extrairIdMotoristaDoJson(response);
                    SessaoUtilizador.setMotoristaId(idMotorista);
                    abrirDashboardUser(event, nome);
                } else {
                    showError("Erro de Login", response != null ? response : "Credenciais inválidas.");
                }
            });
        }).exceptionally(throwable -> {
            javafx.application.Platform.runLater(() -> showError("Erro de Login", "Não foi possível conectar ao servidor."));
            return null;
        });
    }

    // Função utilitária para extrair o nome do JSON da resposta
    private String extrairNomeDoJson(String json) {
        // Simples extração (podes usar uma lib JSON se preferires)
        try {
            int idx = json.indexOf("\"nome\":");
            if (idx != -1) {
                int start = json.indexOf('"', idx + 7) + 1;
                int end = json.indexOf('"', start);
                return json.substring(start, end);
            }
        } catch (Exception e) {
            // Ignorar e devolver vazio
        }
        return "Utilizador";
    }

    // Função utilitária para extrair o idMotorista do JSON da resposta
    private Long extrairIdMotoristaDoJson(String json) {
        try {
            int idx = json.indexOf("\"idMotorista\":");
            if (idx != -1) {
                int start = json.indexOf(":", idx) + 1;
                int end = json.indexOf(",", start);
                if (end == -1) end = json.indexOf("}", start);
                String idStr = json.substring(start, end).replaceAll("[^0-9]", "").trim();
                return Long.parseLong(idStr);
            }
        } catch (Exception e) {
            // Ignorar e devolver null
        }
        return null;
    }

    // --- 2FA ---
    @FXML
    private void handle2FA(ActionEvent event) {
        String code = twofaCodeField.getText();
        Map<String, String> data = new HashMap<>();
        data.put("email", tempEmail);
        data.put("code", code);
        CompletableFuture<String> future = ApiClient.postText("/auth/verify-2fa", data);
        future.thenAccept(response -> {
            javafx.application.Platform.runLater(() -> {
                if (response != null && response.contains("Login bem-sucedido")) {
                    abrirDashboardUser(event);
                } else {
                    showError("2FA", response != null ? response : "Código inválido ou expirado.");
                }
            });
        }).exceptionally(throwable -> {
            javafx.application.Platform.runLater(() -> showError("2FA", "Erro de conexão."));
            return null;
        });
    }

    @FXML
    private void resend2FA(ActionEvent event) {
        // Reenvia o código 2FA
        Map<String, String> data = new HashMap<>();
        data.put("email", tempEmail);
        data.put("password", passwordField.getText());
        CompletableFuture<String> future = ApiClient.postText("/auth/login", data);
        future.thenAccept(response -> {
            javafx.application.Platform.runLater(() -> {
                if (response != null && response.contains("Código 2FA enviado")) {
                    showInfo("Novo código enviado para o email.");
                } else {
                    showError("2FA", response != null ? response : "Erro ao reenviar código.");
                }
            });
        });
    }

    // --- Recuperação de password ---
    @FXML
    private void handleRecoveryRequest(ActionEvent event) {
        String email = recoveryEmailField.getText();
        tempEmail = email;
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        CompletableFuture<String> future = ApiClient.postText("/auth/request-password-reset", data);
        future.thenAccept(response -> {
            javafx.application.Platform.runLater(() -> {
                if (response != null && response.contains("Código de recuperação enviado")) {
                    recoveryStep2Label.setText("Código enviado para o email. Insira o código e a nova password.");
                    recoveryStep2Label.setVisible(true);
                    recoveryCodeField.setVisible(true);
                    newPasswordField.setVisible(true);
                    alterarPasswordButton.setVisible(true);
                } else {
                    showError("Recuperação de Password", response != null ? response : "Erro ao enviar código.");
                }
            });
        });
    }

    @FXML
    private void handleResetPassword(ActionEvent event) {
        String code = recoveryCodeField.getText();
        String newPassword = newPasswordField.getText();
        Map<String, String> data = new HashMap<>();
        data.put("email", tempEmail);
        data.put("code", code);
        data.put("newPassword", newPassword);
        CompletableFuture<String> future = ApiClient.postText("/auth/reset-password", data);
        future.thenAccept(response -> {
            javafx.application.Platform.runLater(() -> {
                if (response != null && response.contains("Password alterada")) {
                    showInfo("Password alterada com sucesso! Faça login.");
                    showLoginStep();
                } else {
                    showError("Recuperação de Password", response != null ? response : "Código inválido ou expirado.");
                }
            });
        });
    }

    // --- Utilitários ---
    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void abrirDashboardAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Admin/AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Dashboard Admin");
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void abrirDashboardUser(ActionEvent event, String nome) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Driver/MotoristaDashboard.fxml"));
            Parent root = loader.load();
            // Passar o nome para o controlador da dashboard
            MotoristaDashboardController controller = loader.getController();
            controller.setNomeUtilizador(nome);
            Stage stage = new Stage();
            stage.setTitle("Dashboard Motorista");
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void abrirJanelaRegisto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Criar Conta");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();
            // Fechar a janela atual de login
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Corrigir chamadas antigas para o novo método
    private void abrirDashboardUser(ActionEvent event) {
        abrirDashboardUser(event, "Utilizador");
    }
}
