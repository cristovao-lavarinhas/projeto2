package com.example.projetojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar o FXML inicial (AdminDashboard, Login, etc.)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/AdminDashboard.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1000, 600);

        // Se quiseres adicionar uma folha de estilo aqui (login.css por exemplo):
        scene.getStylesheets().add(getClass().getResource("/com/example/projetojavafx/login.css").toExternalForm());

        primaryStage.setTitle("DriveSmart");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
