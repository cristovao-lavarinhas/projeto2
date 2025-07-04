package com.example.javafxclient.controller;

import com.example.javafxclient.model.Cliente;
import com.example.javafxclient.model.Motorista;
import com.example.javafxclient.model.Viagem;
import com.example.javafxclient.service.ApiClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MainController {
    
    @FXML private TabPane tabPane;
    @FXML private Tab clientesTab;
    @FXML private Tab motoristasTab;
    @FXML private Tab viagensTab;
    
    // Clientes tab
    @FXML private TableView<Cliente> clientesTable;
    @FXML private TableColumn<Cliente, String> clienteNomeCol;
    @FXML private TableColumn<Cliente, String> clienteNifCol;
    @FXML private TableColumn<Cliente, String> clienteTelCol;
    @FXML private TextField clienteNomeField;
    @FXML private TextField clienteNifField;
    @FXML private TextField clienteTelField;
    @FXML private TextField clienteRuaField;
    @FXML private TextField clientePortaField;
    @FXML private Button addClienteBtn;
    @FXML private Button updateClienteBtn;
    @FXML private Button deleteClienteBtn;
    
    // Motoristas tab
    @FXML private TableView<Motorista> motoristasTable;
    @FXML private TableColumn<Motorista, String> motoristaNomeCol;
    @FXML private TableColumn<Motorista, String> motoristaTelCol;
    @FXML private TableColumn<Motorista, String> motoristaCartaCol;
    
    // Viagens tab
    @FXML private TableView<Viagem> viagensTable;
    @FXML private TableColumn<Viagem, String> viagemIdCol;
    @FXML private TableColumn<Viagem, String> viagemMotoristaCol;
    @FXML private TableColumn<Viagem, String> viagemClienteCol;
    @FXML private TableColumn<Viagem, BigDecimal> viagemPrecoCol;
    
    private ApiClient apiClient;
    private ObservableList<Cliente> clientesList;
    private ObservableList<Motorista> motoristasList;
    private ObservableList<Viagem> viagensList;
    
    @FXML
    public void initialize() {
        apiClient = new ApiClient();
        setupTables();
        setupButtons();
        loadData();
    }
    
    private void setupTables() {
        // Setup clientes table
        clientesList = FXCollections.observableArrayList();
        clientesTable.setItems(clientesList);
        
        clienteNomeCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        clienteNifCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getNif()));
        clienteTelCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getTel()));
        
        // Setup motoristas table
        motoristasList = FXCollections.observableArrayList();
        motoristasTable.setItems(motoristasList);
        
        motoristaNomeCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        motoristaTelCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getTel()));
        motoristaCartaCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCartaConducao()));
        
        // Setup viagens table
        viagensList = FXCollections.observableArrayList();
        viagensTable.setItems(viagensList);
        
        viagemIdCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getIdViagem().toString()));
        viagemMotoristaCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getMotorista() != null ? data.getValue().getMotorista().getNome() : "N/A"));
        viagemClienteCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCliente() != null ? data.getValue().getCliente().getNome() : "N/A"));
        viagemPrecoCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPreco()));
    }
    
    private void setupButtons() {
        addClienteBtn.setOnAction(e -> addCliente());
        updateClienteBtn.setOnAction(e -> updateCliente());
        deleteClienteBtn.setOnAction(e -> deleteCliente());
    }
    
    private void loadData() {
        loadClientes();
        loadMotoristas();
        loadViagens();
    }
    
    private void loadClientes() {
        apiClient.getAllClientes().thenAccept(clientes -> {
            Platform.runLater(() -> {
                if (clientes != null) {
                    clientesList.clear();
                    clientesList.addAll(clientes);
                }
            });
        });
    }
    
    private void loadMotoristas() {
        apiClient.getAllMotoristas().thenAccept(motoristas -> {
            Platform.runLater(() -> {
                if (motoristas != null) {
                    motoristasList.clear();
                    motoristasList.addAll(motoristas);
                }
            });
        });
    }
    
    private void loadViagens() {
        apiClient.getAllViagens().thenAccept(viagens -> {
            Platform.runLater(() -> {
                if (viagens != null) {
                    viagensList.clear();
                    viagensList.addAll(viagens);
                }
            });
        });
    }
    
    private void addCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNome(clienteNomeField.getText());
            cliente.setNif(clienteNifField.getText());
            cliente.setTel(clienteTelField.getText());
            cliente.setRua(clienteRuaField.getText());
            
            String portaText = clientePortaField.getText();
            if (!portaText.isEmpty()) {
                cliente.setNPorta(Integer.parseInt(portaText));
            }
            
            apiClient.createCliente(cliente).thenAccept(createdCliente -> {
                Platform.runLater(() -> {
                    if (createdCliente != null) {
                        clientesList.add(createdCliente);
                        clearClienteFields();
                        showAlert("Success", "Cliente added successfully!");
                    } else {
                        showAlert("Error", "Failed to add cliente!");
                    }
                });
            });
        } catch (Exception e) {
            showAlert("Error", "Invalid input: " + e.getMessage());
        }
    }
    
    private void updateCliente() {
        Cliente selected = clientesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Please select a cliente to update!");
            return;
        }
        
        try {
            selected.setNome(clienteNomeField.getText());
            selected.setNif(clienteNifField.getText());
            selected.setTel(clienteTelField.getText());
            selected.setRua(clienteRuaField.getText());
            
            String portaText = clientePortaField.getText();
            if (!portaText.isEmpty()) {
                selected.setNPorta(Integer.parseInt(portaText));
            }
            
            apiClient.updateCliente(selected.getIdCliente(), selected).thenAccept(updatedCliente -> {
                Platform.runLater(() -> {
                    if (updatedCliente != null) {
                        clientesTable.refresh();
                        clearClienteFields();
                        showAlert("Success", "Cliente updated successfully!");
                    } else {
                        showAlert("Error", "Failed to update cliente!");
                    }
                });
            });
        } catch (Exception e) {
            showAlert("Error", "Invalid input: " + e.getMessage());
        }
    }
    
    private void deleteCliente() {
        Cliente selected = clientesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Please select a cliente to delete!");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Cliente");
        alert.setContentText("Are you sure you want to delete " + selected.getNome() + "?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                apiClient.deleteCliente(selected.getIdCliente()).thenAccept(success -> {
                    Platform.runLater(() -> {
                        if (success) {
                            clientesList.remove(selected);
                            showAlert("Success", "Cliente deleted successfully!");
                        } else {
                            showAlert("Error", "Failed to delete cliente!");
                        }
                    });
                });
            }
        });
    }
    
    private void clearClienteFields() {
        clienteNomeField.clear();
        clienteNifField.clear();
        clienteTelField.clear();
        clienteRuaField.clear();
        clientePortaField.clear();
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void cleanup() {
        if (apiClient != null) {
            apiClient.close();
        }
    }
} 