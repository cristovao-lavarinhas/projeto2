package com.example.projetojavafx.Controller.Driver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EstatisticasController {

    @FXML private ComboBox<String> periodoCombo;
    @FXML private Label totalViagensLabel;
    @FXML private Label viagensCrescimento;
    @FXML private Label ganhosLabel;
    @FXML private Label ganhosCrescimento;
    @FXML private Label avaliacaoLabel;
    @FXML private Label avaliacaoCrescimento;
    @FXML private Label tempoLabel;
    @FXML private Label tempoCrescimento;
    @FXML private VBox graficoViagensContent;
    @FXML private VBox graficoGanhosContent;
    @FXML private TableView<DadosDesempenho> tabelaDesempenho;
    @FXML private TableColumn<DadosDesempenho, String> colData;
    @FXML private TableColumn<DadosDesempenho, Integer> colViagens;
    @FXML private TableColumn<DadosDesempenho, String> colGanhos;
    @FXML private TableColumn<DadosDesempenho, Double> colAvaliacao;
    @FXML private TableColumn<DadosDesempenho, String> colTempo;
    @FXML private VBox metasContainer;
    @FXML private VBox conquistasContainer;

    private ObservableList<DadosDesempenho> dadosDesempenho = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            configurarComboBox();
            configurarTabela();
            carregarDadosMock();
            atualizarEstatisticas();
            criarGraficos();
            carregarMetasConquistas();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar estatísticas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarComboBox() {
        periodoCombo.getItems().addAll("Última Semana", "Último Mês", "Últimos 3 Meses", "Último Ano");
        periodoCombo.setValue("Último Mês");
        periodoCombo.setOnAction(e -> atualizarEstatisticas());
    }

    private void configurarTabela() {
        colData.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getData()));
        colViagens.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getViagens()).asObject());
        colGanhos.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGanhos()));
        colAvaliacao.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getAvaliacao()).asObject());
        colTempo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTempoOnline()));
        
        tabelaDesempenho.setItems(dadosDesempenho);
    }

    private void carregarDadosMock() {
        dadosDesempenho.clear();
        
        LocalDate hoje = LocalDate.now();
        Random random = new Random();
        
        for (int i = 0; i < 30; i++) {
            LocalDate data = hoje.minusDays(i);
            int viagens = random.nextInt(10) + 1;
            double ganhos = viagens * (random.nextDouble() * 50 + 20);
            double avaliacao = 4.0 + random.nextDouble();
            int tempoOnline = random.nextInt(8) + 4;
            
            dadosDesempenho.add(new DadosDesempenho(
                data.format(DateTimeFormatter.ofPattern("dd/MM")),
                viagens,
                String.format("€%.2f", ganhos),
                avaliacao,
                tempoOnline + "h"
            ));
        }
    }

    private void atualizarEstatisticas() {
        // Calcular totais
        int totalViagens = dadosDesempenho.stream().mapToInt(DadosDesempenho::getViagens).sum();
        double totalGanhos = dadosDesempenho.stream()
            .mapToDouble(d -> Double.parseDouble(d.getGanhos().replace("€", "")))
            .sum();
        double mediaAvaliacao = dadosDesempenho.stream()
            .mapToDouble(DadosDesempenho::getAvaliacao)
            .average()
            .orElse(0.0);
        int totalTempo = dadosDesempenho.stream()
            .mapToInt(d -> Integer.parseInt(d.getTempoOnline().replace("h", "")))
            .sum();
        
        // Atualizar labels
        totalViagensLabel.setText(String.valueOf(totalViagens));
        ganhosLabel.setText(String.format("€%.2f", totalGanhos));
        avaliacaoLabel.setText(String.format("%.1f", mediaAvaliacao));
        tempoLabel.setText(totalTempo + "h");
        
        // Crescimento (mock)
        viagensCrescimento.setText("+12%");
        viagensCrescimento.setStyle("-fx-text-fill: #4caf50;");
        ganhosCrescimento.setText("+8%");
        ganhosCrescimento.setStyle("-fx-text-fill: #4caf50;");
        avaliacaoCrescimento.setText("+2%");
        avaliacaoCrescimento.setStyle("-fx-text-fill: #4caf50;");
        tempoCrescimento.setText("+5%");
        tempoCrescimento.setStyle("-fx-text-fill: #4caf50;");
    }

    private void criarGraficos() {
        criarGraficoViagens();
        criarGraficoGanhos();
    }

    private void criarGraficoViagens() {
        graficoViagensContent.getChildren().clear();
        
        VBox grafico = new VBox(5);
        grafico.setAlignment(Pos.BOTTOM_CENTER);
        grafico.setPadding(new Insets(20));
        grafico.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");
        
        // Pegar últimos 7 dias
        List<DadosDesempenho> ultimos7Dias = dadosDesempenho.subList(0, Math.min(7, dadosDesempenho.size()));
        
        HBox barras = new HBox(10);
        barras.setAlignment(Pos.BOTTOM_CENTER);
        
        int maxViagens = ultimos7Dias.stream().mapToInt(DadosDesempenho::getViagens).max().orElse(1);
        
        for (DadosDesempenho dados : ultimos7Dias) {
            VBox barraContainer = new VBox(5);
            barraContainer.setAlignment(Pos.BOTTOM_CENTER);
            
            double altura = (double) dados.getViagens() / maxViagens * 150;
            Rectangle barra = new Rectangle(30, altura);
            barra.setFill(Color.web("#2196f3"));
            barra.setArcWidth(4);
            barra.setArcHeight(4);
            
            Label valor = new Label(String.valueOf(dados.getViagens()));
            valor.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            
            Label dia = new Label(dados.getData());
            dia.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
            
            barraContainer.getChildren().addAll(valor, barra, dia);
            barras.getChildren().add(barraContainer);
        }
        
        grafico.getChildren().add(barras);
        graficoViagensContent.getChildren().add(grafico);
    }

    private void criarGraficoGanhos() {
        graficoGanhosContent.getChildren().clear();
        
        VBox grafico = new VBox(5);
        grafico.setAlignment(Pos.BOTTOM_CENTER);
        grafico.setPadding(new Insets(20));
        grafico.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");
        
        // Pegar últimas 4 semanas
        List<DadosDesempenho> ultimas4Semanas = dadosDesempenho.subList(0, Math.min(28, dadosDesempenho.size()));
        
        HBox barras = new HBox(15);
        barras.setAlignment(Pos.BOTTOM_CENTER);
        
        double maxGanhos = ultimas4Semanas.stream()
            .mapToDouble(d -> Double.parseDouble(d.getGanhos().replace("€", "")))
            .max()
            .orElse(1.0);
        
        for (int i = 0; i < 4; i++) {
            int inicio = i * 7;
            int fim = Math.min(inicio + 7, ultimas4Semanas.size());
            
            double ganhosSemana = ultimas4Semanas.subList(inicio, fim).stream()
                .mapToDouble(d -> Double.parseDouble(d.getGanhos().replace("€", "")))
                .sum();
            
            VBox barraContainer = new VBox(5);
            barraContainer.setAlignment(Pos.BOTTOM_CENTER);
            
            double altura = ganhosSemana / maxGanhos * 150;
            Rectangle barra = new Rectangle(40, altura);
            barra.setFill(Color.web("#4caf50"));
            barra.setArcWidth(4);
            barra.setArcHeight(4);
            
            Label valor = new Label(String.format("€%.0f", ganhosSemana));
            valor.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            
            Label semana = new Label("Sem " + (i + 1));
            semana.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
            
            barraContainer.getChildren().addAll(valor, barra, semana);
            barras.getChildren().add(barraContainer);
        }
        
        grafico.getChildren().add(barras);
        graficoGanhosContent.getChildren().add(grafico);
    }

    private void carregarMetasConquistas() {
        metasContainer.getChildren().clear();
        conquistasContainer.getChildren().clear();
        
        // Metas
        adicionarMeta("Viagens Diárias", "8/10", 80);
        adicionarMeta("Ganhos Semanais", "€450/€500", 90);
        adicionarMeta("Avaliação Média", "4.8/5.0", 96);
        adicionarMeta("Tempo Online", "45h/50h", 90);
        
        // Conquistas
        adicionarConquista("🏆 Primeira Viagem", "Completou sua primeira viagem");
        adicionarConquista("⭐ Avaliação Perfeita", "Recebeu 5 estrelas de um cliente");
        adicionarConquista("💰 Ganhador", "Faturou mais de €100 em um dia");
        adicionarConquista("🚗 Motorista Dedicado", "Trabalhou 7 dias seguidos");
    }

    private void adicionarMeta(String titulo, String progresso, int percentagem) {
        VBox meta = new VBox(5);
        meta.setPadding(new Insets(10));
        meta.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-background-radius: 6;");
        
        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label progressoLabel = new Label(progresso);
        progressoLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");
        
        ProgressBar barra = new ProgressBar(percentagem / 100.0);
        barra.setPrefWidth(150);
        barra.setStyle("-fx-accent: #4caf50;");
        
        meta.getChildren().addAll(tituloLabel, progressoLabel, barra);
        metasContainer.getChildren().add(meta);
    }

    private void adicionarConquista(String titulo, String descricao) {
        VBox conquista = new VBox(5);
        conquista.setPadding(new Insets(10));
        conquista.setStyle("-fx-background-color: #fff3e0; -fx-border-color: #ff9800; -fx-border-radius: 6; -fx-background-radius: 6;");
        
        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #e65100;");
        
        Label descricaoLabel = new Label(descricao);
        descricaoLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");
        
        conquista.getChildren().addAll(tituloLabel, descricaoLabel);
        conquistasContainer.getChildren().add(conquista);
    }

    @FXML
    private void exportarPDF() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportar PDF");
        alert.setHeaderText("Funcionalidade em Desenvolvimento");
        alert.setContentText("A exportação para PDF será implementada em breve.");
        alert.showAndWait();
    }

    public static class DadosDesempenho {
        private String data;
        private int viagens;
        private String ganhos;
        private double avaliacao;
        private String tempoOnline;

        public DadosDesempenho(String data, int viagens, String ganhos, double avaliacao, String tempoOnline) {
            this.data = data;
            this.viagens = viagens;
            this.ganhos = ganhos;
            this.avaliacao = avaliacao;
            this.tempoOnline = tempoOnline;
        }

        public String getData() { return data; }
        public int getViagens() { return viagens; }
        public String getGanhos() { return ganhos; }
        public double getAvaliacao() { return avaliacao; }
        public String getTempoOnline() { return tempoOnline; }
    }
} 