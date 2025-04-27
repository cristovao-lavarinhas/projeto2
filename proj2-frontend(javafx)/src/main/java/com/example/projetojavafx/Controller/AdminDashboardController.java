package com.example.projetojavafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private StackPane contentArea;

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node page = loader.load();
            contentArea.getChildren().setAll(page); // Substitui o conteúdo atual pelo novo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos ligados aos botões
    @FXML
    private void listarMotorista() {
        loadPage("/com/example/projetojavafx/ListarMotoristas.fxml");
    }

    @FXML
    private void pedidosInscricao() {
        loadPage("/com/example/projetojavafx/PedidosInscricao.fxml");
    }

    @FXML
    private void estadoMotorista() {
        loadPage("/com/example/projetojavafx/EstadoMotorista.fxml");
    }

    @FXML
    private void atualizarDocs() {
        loadPage("/com/example/projetojavafx/AtualizarDocumentos.fxml");
    }

    @FXML
    private void estado() {
        loadPage("/com/example/projetojavafx/VerificarEstado.fxml");
    }

    @FXML
    private void registarViatura() {
        loadPage("/com/example/projetojavafx/RegistarViatura.fxml");
    }

    @FXML
    private void associarViatura() {
        loadPage("/com/example/projetojavafx/AssociarViatura.fxml");
    }

    @FXML
    private void gerirDocumentos() {
        loadPage("/com/example/projetojavafx/GerirDocumentos.fxml");
    }

    @FXML
    private void estadoViatura() {
        loadPage("/com/example/projetojavafx/EstadoViatura.fxml");
    }

    @FXML
    private void historicoViagens() {
        loadPage("/com/example/projetojavafx/HistoricoViagens.fxml");
    }

    @FXML
    private void detalhes() {
        loadPage("/com/example/projetojavafx/DetalhesViagens.fxml");
    }

    @FXML
    private void gerirFaturacao() {
        loadPage("/com/example/projetojavafx/GerirFaturacao.fxml");
    }

    @FXML
    private void controlarSaldo() {
        loadPage("/com/example/projetojavafx/ControlarSaldo.fxml");
    }

    @FXML
    private void exportarRelatorios() {
        loadPage("/com/example/projetojavafx/ExportarRelatorios.fxml");
    }

    @FXML
    private void suporteCliente() {
        loadPage("/com/example/projetojavafx/SuporteCliente.fxml");
    }

    @FXML
    private void suporteMotorista() {
        loadPage("/com/example/projetojavafx/SuporteMotorista.fxml");
    }

    @FXML
    private void listarViatura() {
        loadPage("/com/example/projetojavafx/ListarViatura.fxml");
    }



    @FXML
    private void handleLogout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/projetojavafx/Login.fxml"));
        contentArea.getScene().setRoot(root);
    }

}
