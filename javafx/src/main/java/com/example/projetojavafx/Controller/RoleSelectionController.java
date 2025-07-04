package com.example.projetojavafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class RoleSelectionController {

    @FXML
    private ToggleButton adminButton;

    @FXML
    private ToggleButton driverButton;

    private String selectedRole = null;

    @FXML
    private void initialize() {
        adminButton.getStyleClass().add("role-button");
        driverButton.getStyleClass().add("role-button");
    }

    @FXML
    private void handleAdminSelected() {
        selectedRole = "ADMIN";
        highlightSelection();
    }

    @FXML
    private void handleDriverSelected() {
        selectedRole = "MOTORISTA";
        highlightSelection();
    }

    private void highlightSelection() {
        if ("ADMIN".equals(selectedRole)) {
            adminButton.getStyleClass().setAll("role-button-selected");
            driverButton.getStyleClass().setAll("role-button");
        } else if ("MOTORISTA".equals(selectedRole)) {
            driverButton.getStyleClass().setAll("role-button-selected");
            adminButton.getStyleClass().setAll("role-button");
        }
    }

    @FXML
    private void handleContinue(ActionEvent event) {
        if (selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção obrigatória");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecione um cargo antes de continuar.");
            alert.showAndWait();
        } else {
            try {
                String fxmlFile = "";

                if ("ADMIN".equals(selectedRole)) {
                    fxmlFile = "/com/example/projetojavafx/LoginAdmin.fxml";
                } else if ("MOTORISTA".equals(selectedRole)) {
                    fxmlFile = "/com/example/projetojavafx/LoginMotorista.fxml";
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Login - " + selectedRole);
                stage.setScene(new Scene(root));
                stage.show();

                // Fechar a janela atual (RoleSelection)
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCancel() {
        System.exit(0);
    }
}
