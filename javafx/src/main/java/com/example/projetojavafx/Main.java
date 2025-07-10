package com.example.projetojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar o FXML inicial (AdminDashboard, Login, etc.)
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Admin/AdminDashboard.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Driver/MotoristaDashboard.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projetojavafx/Splash.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        scene.setFill(Color.WHITE);
        // Se quiseres adicionar uma folha de estilo aqui (login.css por exemplo):
        scene.getStylesheets().add(getClass().getResource("/com/example/projetojavafx/login.css").toExternalForm());

        primaryStage.setTitle("DriveSmart");

        primaryStage.setMaximized(true);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
