package com.example.projetojavafx.Controller.Admin;

import com.example.projetojavafx.Modelo.Viagem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetalhesViagemController {

    @FXML private Label motoristaLabel;
    @FXML private Label partidaLabel;
    @FXML private Label destinoLabel;
    @FXML private Label dataLabel;
    @FXML private Label estadoLabel;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setViagem(Viagem viagem) {
        motoristaLabel.setText("Motorista: " + viagem.getMotorista());
        partidaLabel.setText("Partida: " + viagem.getPartida());
        destinoLabel.setText("Destino: " + viagem.getDestino());
        dataLabel.setText("Data da Viagem: " + viagem.getDataViagem());
        estadoLabel.setText("Estado: " + viagem.getEstado());
    }

    @FXML
    private void fecharJanela() {
        if (stage != null) {
            stage.close();
        }
    }

}
