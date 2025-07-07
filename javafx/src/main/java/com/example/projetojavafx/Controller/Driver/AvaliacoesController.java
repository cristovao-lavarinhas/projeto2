package com.example.projetojavafx.Controller.Driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AvaliacoesController {

    @FXML private ComboBox<String> filtroCombo;
    @FXML private Label avaliacaoGeral;
    @FXML private Label totalAvaliacoes;
    @FXML private VBox distribuicaoContainer;
    @FXML private Label avaliacoesPositivas;
    @FXML private Label avaliacoesNeutras;
    @FXML private Label avaliacoesNegativas;
    @FXML private VBox listaAvaliacoes;
    @FXML private Label contadorAvaliacoes;
    @FXML private Button paginaAnterior;
    @FXML private Button paginaProxima;
    @FXML private Label paginaAtual;

    private ObservableList<Avaliacao> avaliacoes = FXCollections.observableArrayList();
    private int paginaAtualNum = 1;
    private int itensPorPagina = 5;
    private List<Avaliacao> todasAvaliacoes = new ArrayList<>();

    @FXML
    public void initialize() {
        try {
            configurarFiltros();
            carregarDadosMock();
            atualizarEstatisticas();
            atualizarListaAvaliacoes();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar avaliações: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarFiltros() {
        filtroCombo.getItems().addAll("Todas", "5 Estrelas", "4 Estrelas", "3 Estrelas", "2 Estrelas", "1 Estrela", "Não Respondidas");
        filtroCombo.setValue("Todas");
        filtroCombo.setOnAction(e -> filtrarAvaliacoes());
    }

    private void carregarDadosMock() {
        todasAvaliacoes.clear();
        
        // Criar avaliações de exemplo
        todasAvaliacoes.add(new Avaliacao("João Silva", 5, "Excelente serviço! Motorista muito profissional e pontual.", LocalDateTime.now().minusDays(1), false));
        todasAvaliacoes.add(new Avaliacao("Maria Santos", 5, "Viagem muito confortável e segura. Recomendo!", LocalDateTime.now().minusDays(2), true));
        todasAvaliacoes.add(new Avaliacao("Pedro Costa", 4, "Bom serviço, mas poderia ter sido mais pontual.", LocalDateTime.now().minusDays(3), false));
        todasAvaliacoes.add(new Avaliacao("Ana Oliveira", 5, "Motorista muito simpático e o carro estava impecável.", LocalDateTime.now().minusDays(4), false));
        todasAvaliacoes.add(new Avaliacao("Carlos Ferreira", 3, "Serviço regular. O carro poderia estar mais limpo.", LocalDateTime.now().minusDays(5), true));
        todasAvaliacoes.add(new Avaliacao("Sofia Martins", 5, "Perfeito! Cheguei ao destino antes do previsto.", LocalDateTime.now().minusDays(6), false));
        todasAvaliacoes.add(new Avaliacao("Miguel Rodrigues", 4, "Boa viagem, motorista atencioso.", LocalDateTime.now().minusDays(7), false));
        todasAvaliacoes.add(new Avaliacao("Inês Pereira", 2, "Atrasou muito e não foi muito profissional.", LocalDateTime.now().minusDays(8), true));
        todasAvaliacoes.add(new Avaliacao("Tiago Sousa", 5, "Excelente! Muito profissional e pontual.", LocalDateTime.now().minusDays(9), false));
        todasAvaliacoes.add(new Avaliacao("Beatriz Lima", 4, "Bom serviço, recomendo.", LocalDateTime.now().minusDays(10), false));
        
        avaliacoes.setAll(todasAvaliacoes);
    }

    private void atualizarEstatisticas() {
        int total = todasAvaliacoes.size();
        double media = todasAvaliacoes.stream().mapToInt(Avaliacao::getEstrelas).average().orElse(0.0);
        
        avaliacaoGeral.setText(String.format("%.1f", media));
        totalAvaliacoes.setText("Baseado em " + total + " avaliações");
        
        // Calcular distribuição
        Map<Integer, Long> distribuicao = new HashMap<>();
        distribuicao.put(1, todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 1).count());
        distribuicao.put(2, todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 2).count());
        distribuicao.put(3, todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 3).count());
        distribuicao.put(4, todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 4).count());
        distribuicao.put(5, todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 5).count());
        
        criarDistribuicao(distribuicao, total);
        
        // Calcular percentagens
        long positivas = todasAvaliacoes.stream().filter(a -> a.getEstrelas() >= 4).count();
        long neutras = todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 3).count();
        long negativas = todasAvaliacoes.stream().filter(a -> a.getEstrelas() <= 2).count();
        
        avaliacoesPositivas.setText(String.format("%.0f%%", (double) positivas / total * 100));
        avaliacoesNeutras.setText(String.format("%.0f%%", (double) neutras / total * 100));
        avaliacoesNegativas.setText(String.format("%.0f%%", (double) negativas / total * 100));
    }

    private void criarDistribuicao(Map<Integer, Long> distribuicao, int total) {
        distribuicaoContainer.getChildren().clear();
        
        for (int estrelas = 5; estrelas >= 1; estrelas--) {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER_LEFT);
            
            Label estrelasLabel = new Label(estrelas + "★");
            estrelasLabel.setPrefWidth(30);
            estrelasLabel.setStyle("-fx-font-size: 12px;");
            
            ProgressBar barra = new ProgressBar();
            barra.setPrefWidth(150);
            barra.setPrefHeight(8);
            
            long quantidade = distribuicao.getOrDefault(estrelas, 0L);
            double percentagem = total > 0 ? (double) quantidade / total : 0.0;
            barra.setProgress(percentagem);
            
            // Cor da barra baseada no número de estrelas
            if (estrelas >= 4) {
                barra.setStyle("-fx-accent: #4caf50;");
            } else if (estrelas == 3) {
                barra.setStyle("-fx-accent: #ff9800;");
            } else {
                barra.setStyle("-fx-accent: #f44336;");
            }
            
            Label quantidadeLabel = new Label(String.valueOf(quantidade));
            quantidadeLabel.setPrefWidth(30);
            quantidadeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
            
            linha.getChildren().addAll(estrelasLabel, barra, quantidadeLabel);
            distribuicaoContainer.getChildren().add(linha);
        }
    }

    private void filtrarAvaliacoes() {
        String filtro = filtroCombo.getValue();
        List<Avaliacao> filtradas = new ArrayList<>();
        
        switch (filtro) {
            case "Todas":
                filtradas = new ArrayList<>(todasAvaliacoes);
                break;
            case "5 Estrelas":
                filtradas = todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 5).collect(java.util.stream.Collectors.toList());
                break;
            case "4 Estrelas":
                filtradas = todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 4).collect(java.util.stream.Collectors.toList());
                break;
            case "3 Estrelas":
                filtradas = todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 3).collect(java.util.stream.Collectors.toList());
                break;
            case "2 Estrelas":
                filtradas = todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 2).collect(java.util.stream.Collectors.toList());
                break;
            case "1 Estrela":
                filtradas = todasAvaliacoes.stream().filter(a -> a.getEstrelas() == 1).collect(java.util.stream.Collectors.toList());
                break;
            case "Não Respondidas":
                filtradas = todasAvaliacoes.stream().filter(a -> a.isRespondida()).collect(java.util.stream.Collectors.toList());
                break;
        }
        
        avaliacoes.setAll(filtradas);
        paginaAtualNum = 1;
        atualizarListaAvaliacoes();
    }

    private void atualizarListaAvaliacoes() {
        listaAvaliacoes.getChildren().clear();
        
        int inicio = (paginaAtualNum - 1) * itensPorPagina;
        int fim = Math.min(inicio + itensPorPagina, avaliacoes.size());
        
        for (int i = inicio; i < fim; i++) {
            Avaliacao avaliacao = avaliacoes.get(i);
            VBox card = criarCardAvaliacao(avaliacao);
            listaAvaliacoes.getChildren().add(card);
        }
        
        contadorAvaliacoes.setText("(" + avaliacoes.size() + " avaliações)");
        
        int totalPaginas = (int) Math.ceil((double) avaliacoes.size() / itensPorPagina);
        paginaAtual.setText("Página " + paginaAtualNum + " de " + totalPaginas);
        
        paginaAnterior.setDisable(paginaAtualNum <= 1);
        paginaProxima.setDisable(paginaAtualNum >= totalPaginas);
    }

    private VBox criarCardAvaliacao(Avaliacao avaliacao) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");
        
        // Cabeçalho
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label nomeCliente = new Label(avaliacao.getCliente());
        nomeCliente.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        
        HBox estrelas = new HBox(2);
        for (int i = 1; i <= 5; i++) {
            Label estrela = new Label(i <= avaliacao.getEstrelas() ? "★" : "☆");
            estrela.setStyle("-fx-font-size: 18px; -fx-text-fill: " + (i <= avaliacao.getEstrelas() ? "#ffd700" : "#ddd") + ";");
            estrelas.getChildren().add(estrela);
        }
        
        Label data = new Label(avaliacao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        data.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        if (avaliacao.isRespondida()) {
            Label respondida = new Label("Respondida");
            respondida.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 4; -fx-font-size: 10px;");
            header.getChildren().addAll(nomeCliente, estrelas, spacer, respondida, data);
        } else {
            Button responder = new Button("Responder");
            responder.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 4; -fx-font-size: 10px;");
            responder.setOnAction(e -> responderAvaliacao(avaliacao));
            header.getChildren().addAll(nomeCliente, estrelas, spacer, responder, data);
        }
        
        // Comentário
        Label comentario = new Label(avaliacao.getComentario());
        comentario.setWrapText(true);
        comentario.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        
        card.getChildren().addAll(header, comentario);
        
        return card;
    }

    private void responderAvaliacao(Avaliacao avaliacao) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Responder Avaliação");
        dialog.setHeaderText("Responder à avaliação de " + avaliacao.getCliente());
        dialog.setContentText("Digite sua resposta:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(resposta -> {
            avaliacao.setRespondida(true);
            avaliacao.setResposta(resposta);
            atualizarListaAvaliacoes();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resposta Enviada");
            alert.setHeaderText("Sucesso!");
            alert.setContentText("Sua resposta foi enviada com sucesso.");
            alert.showAndWait();
        });
    }

    @FXML
    private void responderTodas() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Responder Todas");
        alert.setHeaderText("Responder Avaliações");
        alert.setContentText("Deseja responder a todas as avaliações não respondidas?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Implementar lógica para responder todas
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Funcionalidade");
            info.setHeaderText("Em Desenvolvimento");
            info.setContentText("Esta funcionalidade será implementada em breve.");
            info.showAndWait();
        }
    }

    @FXML
    private void paginaAnterior() {
        if (paginaAtualNum > 1) {
            paginaAtualNum--;
            atualizarListaAvaliacoes();
        }
    }

    @FXML
    private void paginaProxima() {
        int totalPaginas = (int) Math.ceil((double) avaliacoes.size() / itensPorPagina);
        if (paginaAtualNum < totalPaginas) {
            paginaAtualNum++;
            atualizarListaAvaliacoes();
        }
    }

    public static class Avaliacao {
        private String cliente;
        private int estrelas;
        private String comentario;
        private LocalDateTime data;
        private boolean respondida;
        private String resposta;

        public Avaliacao(String cliente, int estrelas, String comentario, LocalDateTime data, boolean respondida) {
            this.cliente = cliente;
            this.estrelas = estrelas;
            this.comentario = comentario;
            this.data = data;
            this.respondida = respondida;
        }

        public String getCliente() { return cliente; }
        public int getEstrelas() { return estrelas; }
        public String getComentario() { return comentario; }
        public LocalDateTime getData() { return data; }
        public boolean isRespondida() { return respondida; }
        public String getResposta() { return resposta; }
        
        public void setRespondida(boolean respondida) { this.respondida = respondida; }
        public void setResposta(String resposta) { this.resposta = resposta; }
    }
} 