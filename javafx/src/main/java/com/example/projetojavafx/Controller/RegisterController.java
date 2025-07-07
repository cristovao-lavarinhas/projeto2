package com.example.projetojavafx.Controller;

import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
    private DatePicker dataNascimentoField;

    @FXML
    private void handleRegister(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String telefone = telefoneField.getText();
        String cartaConducao = cartaConducaoField.getText();
        LocalDate dataNascimento = dataNascimentoField.getValue();

        // Validação básica
        if (nome.isEmpty() || email.isEmpty() || password.isEmpty() || telefone.isEmpty() || cartaConducao.isEmpty() || dataNascimento == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Registo");
            alert.setHeaderText("Campos obrigatórios");
            alert.setContentText("Por favor, preencha todos os campos obrigatórios.");
            alert.showAndWait();
            return;
        }

        // Criar mapa com os dados do registo
        Map<String, Object> registoData = new HashMap<>();
        registoData.put("nome", nome);
        registoData.put("email", email);
        registoData.put("password", password);
        registoData.put("telefone", telefone);
        registoData.put("cartaConducao", cartaConducao);
        registoData.put("dataNascimento", dataNascimento.toString());

        // Enviar para o backend usando o endpoint de registo
        CompletableFuture<Map> future = ApiClient.post("/usuarios/registar", registoData, Map.class);
        
        future.thenAccept(result -> {
            if (result != null && result.containsKey("message")) {
                // Registo bem-sucedido
                javafx.application.Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registo Bem-sucedido");
                    alert.setHeaderText("Conta criada com sucesso!");
                    alert.setContentText("O seu registo foi concluído. Pode agora fazer login.");
                    alert.showAndWait();
                    
                    // Voltar para a tela de login
                    voltarParaLogin(event);
                });
            } else {
                // Erro no registo
                final String errorMessage;
                if (result != null && result.containsKey("error")) {
                    errorMessage = (String) result.get("error");
                } else {
                    errorMessage = "Ocorreu um erro ao criar a conta. Tente novamente.";
                }
                
                javafx.application.Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Registo");
                    alert.setHeaderText("Falha no registo");
                    alert.setContentText(errorMessage);
                    alert.showAndWait();
                });
            }
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
            stage.setResizable(false); // igual ao login
            stage.centerOnScreen();    // centraliza
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
            stage.setResizable(false); // igual ao login
            stage.centerOnScreen();    // centraliza
            stage.show();

            // Fechar a janela atual de registo
            Stage registerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            registerStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 