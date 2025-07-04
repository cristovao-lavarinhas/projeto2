package com.example.projetojavafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.Node;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText("Credenciais inválidas");
            alert.setContentText("Por favor, tente novamente.");
            alert.showAndWait();
        }
    }

}
