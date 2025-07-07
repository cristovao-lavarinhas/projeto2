package com.example.projetojavafx.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.example.projetojavafx.Service.ApiClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    public ImageView logoImageView;
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Verificar se é admin
        if ("admin@admin.com".equals(email) && "admin".equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Admin/AdminDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Dashboard Admin");
                stage.setScene(new Scene(root));

                // AQUI: fullscreen
                stage.setFullScreen(true);
                stage.setFullScreenExitHint(""); // (opcional) tira aquela mensagem chata "Press ESC to exit full screen"

                stage.show();

                // Fechar a janela atual de login
                Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loginStage.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Tentar login com o backend
            Map<String, String> loginData = new HashMap<>();
            loginData.put("email", email);
            loginData.put("password", password);

            CompletableFuture<Map> future = ApiClient.post("/usuarios/login", loginData, Map.class);
            
            future.thenAccept(result -> {
                if (result != null && result.containsKey("message")) {
                    // Login bem-sucedido
                    javafx.application.Platform.runLater(() -> {
                        try {
                            // Verificar o tipo de usuário e redirecionar adequadamente
                            Map<String, Object> usuario = (Map<String, Object>) result.get("usuario");
                            String tipo = (String) usuario.get("tipo");
                            
                            String fxmlPath;
                            String title;
                            
                            if ("MOTORISTA".equals(tipo)) {
                                fxmlPath = "/com/example/projetojavafx/Driver/MotoristaDashboard.fxml";
                                title = "Dashboard Motorista";
                            } else {
                                fxmlPath = "/com/example/projetojavafx/Driver/MotoristaDashboard.fxml";
                                title = "Dashboard";
                            }
                            
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                            Parent root = loader.load();

                            Stage stage = new Stage();
                            stage.setTitle(title);
                            stage.setScene(new Scene(root));
                            stage.setFullScreen(true);
                            stage.setFullScreenExitHint("");

                            stage.show();

                            // Fechar a janela atual de login
                            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            loginStage.close();
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Login falhou
                    final String errorMessage;
                    if (result != null && result.containsKey("error")) {
                        errorMessage = (String) result.get("error");
                    } else {
                        errorMessage = "Credenciais inválidas. Por favor, tente novamente.";
                    }
                    
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro de Login");
                        alert.setHeaderText("Falha no login");
                        alert.setContentText(errorMessage);
                        alert.showAndWait();
                    });
                }
            }).exceptionally(throwable -> {
                javafx.application.Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Login");
                    alert.setHeaderText("Erro de conexão");
                    alert.setContentText("Não foi possível conectar ao servidor. Verifique a sua conexão.");
                    alert.showAndWait();
                });
                return null;
            });
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Criar Conta");
            stage.setScene(new Scene(root));
            stage.show();

            // Fechar a janela atual de login
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirJanelaRegisto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Criar Conta");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            // Fechar a janela atual de login
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
