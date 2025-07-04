package com.example.projetojavafx.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.projetojavafx.Modelo.BackendViatura;
import com.example.projetojavafx.Modelo.Viatura;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ViaturaService {

    private static ObservableList<Viatura> viaturas = FXCollections.observableArrayList();

    // Convert frontend Viatura to backend Viatura
    private static BackendViatura convertToBackend(Viatura viatura) {
        BackendViatura backendViatura = new BackendViatura();
        backendViatura.setMatricula(viatura.getMatricula());
        backendViatura.setModelo(viatura.getModelo());
        try {
            backendViatura.setAno(Integer.parseInt(viatura.getAno()));
        } catch (NumberFormatException e) {
            backendViatura.setAno(2024); // Default year
        }
        return backendViatura;
    }

    // Convert backend Viatura to frontend Viatura
    private static Viatura convertToFrontend(BackendViatura backendViatura) {
        return new Viatura(
            "Unknown", // marca - not in backend model
            backendViatura.getModelo(),
            backendViatura.getMatricula(),
            String.valueOf(backendViatura.getAno()),
            "Unknown", // cor - not in backend model
            true // seguroAtivo - not in backend model
        );
    }

    public static void adicionarViatura(Viatura viatura) {
        // Add to local list immediately for UI responsiveness
        viaturas.add(viatura);
        System.out.println("Viatura registada: " + viatura.getMarca() + " " + viatura.getModelo());

        // Send to backend
        BackendViatura backendViatura = convertToBackend(viatura);
        CompletableFuture<BackendViatura> future = ApiClient.post("/viaturas", backendViatura, BackendViatura.class);
        
        future.thenAccept(result -> {
            if (result != null) {
                System.out.println("Viatura saved to backend with ID: " + result.getIdViatura());
            } else {
                System.err.println("Failed to save viatura to backend");
            }
        }).exceptionally(throwable -> {
            System.err.println("Error saving viatura to backend: " + throwable.getMessage());
            return null;
        });
    }

    public static ObservableList<Viatura> listarViaturas() {
        return viaturas;
    }

    // Load viaturas from backend
    public static void carregarViaturasDoBackend() {
        CompletableFuture<List<BackendViatura>> future = ApiClient.getList("/viaturas", BackendViatura.class);
        
        future.thenAccept(backendViaturas -> {
            if (backendViaturas != null) {
                Platform.runLater(() -> {
                    viaturas.clear();
                    for (BackendViatura backendViatura : backendViaturas) {
                        Viatura viatura = convertToFrontend(backendViatura);
                        viaturas.add(viatura);
                    }
                    System.out.println("Loaded " + viaturas.size() + " viaturas from backend");
                });
            }
        }).exceptionally(throwable -> {
            System.err.println("Error loading viaturas from backend: " + throwable.getMessage());
            return null;
        });
    }

    // Update viatura
    public static void atualizarViatura(Viatura viatura, int index) {
        if (index >= 0 && index < viaturas.size()) {
            viaturas.set(index, viatura);
            
            BackendViatura backendViatura = convertToBackend(viatura);
            // Note: You'll need the actual ID from backend for proper update
            CompletableFuture<BackendViatura> future = ApiClient.put("/viaturas/1", backendViatura, BackendViatura.class);
            
            future.thenAccept(result -> {
                if (result != null) {
                    System.out.println("Viatura updated in backend");
                } else {
                    System.err.println("Failed to update viatura in backend");
                }
            }).exceptionally(throwable -> {
                System.err.println("Error updating viatura in backend: " + throwable.getMessage());
                return null;
            });
        }
    }

    // Delete viatura
    public static void deletarViatura(int index) {
        if (index >= 0 && index < viaturas.size()) {
            Viatura viatura = viaturas.get(index);
            viaturas.remove(index);
            
            // Note: You'll need the actual ID from backend for proper deletion
            CompletableFuture<Boolean> future = ApiClient.delete("/viaturas/1");
            
            future.thenAccept(success -> {
                if (success) {
                    System.out.println("Viatura deleted from backend");
                } else {
                    System.err.println("Failed to delete viatura from backend");
                }
            }).exceptionally(throwable -> {
                System.err.println("Error deleting viatura from backend: " + throwable.getMessage());
                return null;
            });
        }
    }
}
