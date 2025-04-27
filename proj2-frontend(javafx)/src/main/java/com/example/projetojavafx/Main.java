
package com.example.projetojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Splash.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/RoleSelection.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/AdminDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        // Adicionar o CSS externo
        scene.getStylesheets().add(getClass().getResource("/com/example/projetojavafx/login.css").toExternalForm());
        stage.setTitle("DriveSmart");
        stage.setScene(scene);
        // AQUI: fullscreen
        stage.setFullScreen(true);
        stage.setFullScreenExitHint(""); //
        stage.show();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace(); // Mostra a causa real
        }
    }
}