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

public class RegisterController {

    public ImageView logoImageView;
    
    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField telefoneField;

    @FXML
    private TextField cartaConducaoField;

    @FXML
    private void handleRegister(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String telefone = telefoneField.getText();
        String cartaConducao = cartaConducaoField.getText();

        if (nome.isEmpty() || email.isEmpty() || password.isEmpty() || telefone.isEmpty() || cartaConducao.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Registo");
            alert.setHeaderText("Campos obrigatórios");
            alert.setContentText("Por favor, preencha todos os campos obrigatórios.");
            alert.showAndWait();
            return;
        }

        Map<String, Object> registoData = new HashMap<>();
        registoData.put("nome", nome);
        registoData.put("email", email);
        registoData.put("password", password);
        registoData.put("telefone", telefone);
        registoData.put("cartaConducao", cartaConducao);

        CompletableFuture<String> future = ApiClient.postText("/auth/register-motorista", registoData);
        future.thenAccept(response -> {
            javafx.application.Platform.runLater(() -> {
                if (response != null && response.contains("sucesso")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registo Bem-sucedido");
                    alert.setHeaderText("Conta de motorista criada com sucesso!");
                    alert.setContentText("O seu registo foi recebido e está em análise. Receberá uma notificação quando for aprovado.");
                    alert.showAndWait();
                    voltarParaLogin(event);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Registo");
                    alert.setHeaderText("Falha no registo");
                    alert.setContentText(response != null ? response : "Ocorreu um erro ao criar a conta. Tente novamente.");
                    alert.showAndWait();
                }
            });
        }).exceptionally(throwable -> {
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de Registo");
                alert.setHeaderText("Erro de conexão");
                alert.setContentText("Não foi possível conectar ao servidor. Verifique a sua conexão.");
                alert.showAndWait();
            });
            return null;
        });
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

    @FXML
    private void voltarParaLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();

            // Fechar a janela atual de registo
            Stage registerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            registerStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 