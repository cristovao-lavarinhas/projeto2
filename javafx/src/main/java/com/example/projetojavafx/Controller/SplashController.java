package com.example.projetojavafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class SplashController {

    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize() {
        // Carregar o logotipo
        Image logo = new Image(getClass().getResource("/com/example/projetojavafx/icons/logo1.png").toExternalForm());
        logoImage.setImage(logo);
        logoImage.setPreserveRatio(true);
        logoImage.setFitWidth(400);

        // Esperar 2 segundos e depois mudar para a página de seleção
        PauseTransition wait = new PauseTransition(Duration.seconds(3));
        wait.setOnFinished(event -> openRoleSelection());
        wait.play();
    }

    private void openRoleSelection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 600);
            scene.getStylesheets().add(getClass().getResource("/com/example/projetojavafx/style.css").toExternalForm());

            Stage stage = (Stage) logoImage.getScene().getWindow();
            stage.setScene(scene);
            // AQUI: fullscreen
            stage.setFullScreen(true);
            stage.setFullScreenExitHint(""); //
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
