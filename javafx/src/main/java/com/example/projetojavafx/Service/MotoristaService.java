package com.example.projetojavafx.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.projetojavafx.Modelo.BackendMotorista;
import com.example.projetojavafx.Modelo.Motorista;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MotoristaService {

    private static ObservableList<Motorista> motoristas = FXCollections.observableArrayList();

    // Convert frontend Motorista to backend Motorista
    private static BackendMotorista convertToBackend(Motorista motorista) {
        BackendMotorista backendMotorista = new BackendMotorista();
        backendMotorista.setNome(motorista.getNome());
        backendMotorista.setTel(motorista.getTelefone());
        backendMotorista.setCartaConducao(motorista.getLicenca());
        // Set a default date if needed
        backendMotorista.setDtNascimento(new Date());
        return backendMotorista;
    }

    // Convert backend Motorista to frontend Motorista
    private static Motorista convertToFrontend(BackendMotorista backendMotorista) {
        return new Motorista(
            backendMotorista.getNome(),
            backendMotorista.getTel(),
            backendMotorista.getCartaConducao(),
            "Ativo" // Default state
        );
    }

    public static void adicionarMotorista(Motorista motorista) {
        // Add to local list immediately for UI responsiveness
        motoristas.add(motorista);
        System.out.println("Motorista adicionado: " + motorista.getNome());

        // Send to backend
        BackendMotorista backendMotorista = convertToBackend(motorista);
        CompletableFuture<BackendMotorista> future = ApiClient.post("/motoristas", backendMotorista, BackendMotorista.class);
        
        future.thenAccept(result -> {
            if (result != null) {
                System.out.println("Motorista saved to backend with ID: " + result.getIdMotorista());
            } else {
                System.err.println("Failed to save motorista to backend");
            }
        }).exceptionally(throwable -> {
            System.err.println("Error saving motorista to backend: " + throwable.getMessage());
            return null;
        });
    }

    public static ObservableList<Motorista> listarMotoristas() {
        return motoristas;
    }

    // Load motoristas from backend
    public static void carregarMotoristasDoBackend() {
        CompletableFuture<List<BackendMotorista>> future = ApiClient.getList("/motoristas", BackendMotorista.class);
        
        future.thenAccept(backendMotoristas -> {
            if (backendMotoristas != null) {
                Platform.runLater(() -> {
                    motoristas.clear();
                    for (BackendMotorista backendMotorista : backendMotoristas) {
                        Motorista motorista = convertToFrontend(backendMotorista);
                        motoristas.add(motorista);
                    }
                    System.out.println("Loaded " + motoristas.size() + " motoristas from backend");
                });
            }
        }).exceptionally(throwable -> {
            System.err.println("Error loading motoristas from backend: " + throwable.getMessage());
            return null;
        });
    }

    // Update motorista
    public static void atualizarMotorista(Motorista motorista, int index) {
        if (index >= 0 && index < motoristas.size()) {
            motoristas.set(index, motorista);
            
            BackendMotorista backendMotorista = convertToBackend(motorista);
            // Note: You'll need the actual ID from backend for proper update
            CompletableFuture<BackendMotorista> future = ApiClient.put("/motoristas/1", backendMotorista, BackendMotorista.class);
            
            future.thenAccept(result -> {
                if (result != null) {
                    System.out.println("Motorista updated in backend");
                } else {
                    System.err.println("Failed to update motorista in backend");
                }
            }).exceptionally(throwable -> {
                System.err.println("Error updating motorista in backend: " + throwable.getMessage());
                return null;
            });
        }
    }

    // Delete motorista
    public static void deletarMotorista(int index) {
        if (index >= 0 && index < motoristas.size()) {
            Motorista motorista = motoristas.get(index);
            motoristas.remove(index);
            
            // Note: You'll need the actual ID from backend for proper deletion
            CompletableFuture<Boolean> future = ApiClient.delete("/motoristas/1");
            
            future.thenAccept(success -> {
                if (success) {
                    System.out.println("Motorista deleted from backend");
                } else {
                    System.err.println("Failed to delete motorista from backend");
                }
            }).exceptionally(throwable -> {
                System.err.println("Error deleting motorista from backend: " + throwable.getMessage());
                return null;
            });
        }
    }
}
