package com.example.projetojavafx.Controller.Driver;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DisponibilidadeController {

    @FXML private ToggleButton statusToggle;
    @FXML private Label statusInfo;
    @FXML private ComboBox<String> horaInicio;
    @FXML private ComboBox<String> horaFim;
    @FXML private ComboBox<String> intervaloDescanso;
    @FXML private VBox calendarioSemanal;
    @FXML private ComboBox<String> tipoViagemCombo;
    @FXML private ComboBox<String> distanciaMaximaCombo;
    @FXML private TextField tarifaMinimaField;
    @FXML private CheckBox notificacoesViagem;
    @FXML private CheckBox modoAutomatico;
    @FXML private CheckBox modoSilencioso;
    @FXML private TableView<DadosDisponibilidade> tabelaHistorico;
    @FXML private TableColumn<DadosDisponibilidade, String> colData;
    @FXML private TableColumn<DadosDisponibilidade, String> colHoraInicio;
    @FXML private TableColumn<DadosDisponibilidade, String> colHoraFim;
    @FXML private TableColumn<DadosDisponibilidade, String> colTempoOnline;
    @FXML private TableColumn<DadosDisponibilidade, Integer> colViagensAceites;
    @FXML private TableColumn<DadosDisponibilidade, String> colStatus;

    private ObservableList<DadosDisponibilidade> historicoDisponibilidade = FXCollections.observableArrayList();
    private boolean disponivel = true;
    private Map<String, Boolean> disponibilidadeSemanal = new HashMap<>();

    @FXML
    public void initialize() {
        try {
            configurarComboBoxes();
            configurarCalendarioSemanal();
            configurarTabela();
            carregarDadosMock();
            atualizarStatus();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar disponibilidade: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarComboBoxes() {
        // Horários
        List<String> horarios = new ArrayList<>();
        for (int hora = 0; hora < 24; hora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                LocalTime time = LocalTime.of(hora, minuto);
                horarios.add(time.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        }
        
        horaInicio.getItems().addAll(horarios);
        horaFim.getItems().addAll(horarios);
        horaInicio.setValue("08:00");
        horaFim.setValue("18:00");
        
        // Intervalos de descanso
        intervaloDescanso.getItems().addAll("15 min", "30 min", "45 min", "1 hora", "1h 30min", "2 horas");
        intervaloDescanso.setValue("30 min");
        
        // Tipo de viagem
        tipoViagemCombo.getItems().addAll("Todas", "Urbana", "Interurbana", "Aeroporto", "Longa Distância");
        tipoViagemCombo.setValue("Todas");
        
        // Distância máxima
        distanciaMaximaCombo.getItems().addAll("Sem limite", "10 km", "25 km", "50 km", "100 km", "200 km");
        distanciaMaximaCombo.setValue("Sem limite");
    }

    private void configurarCalendarioSemanal() {
        calendarioSemanal.getChildren().clear();
        
        String[] diasSemana = {"Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
        
        for (String dia : diasSemana) {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER_LEFT);
            linha.setPadding(new Insets(8));
            linha.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 4; -fx-background-radius: 4;");
            
            Label diaLabel = new Label(dia);
            diaLabel.setPrefWidth(80);
            diaLabel.setStyle("-fx-font-weight: bold;");
            
            CheckBox disponivelCheck = new CheckBox("Disponível");
            disponivelCheck.setSelected(true);
            disponivelCheck.setOnAction(e -> {
                disponibilidadeSemanal.put(dia, disponivelCheck.isSelected());
            });
            
            ComboBox<String> horaInicioDia = new ComboBox<>();
            ComboBox<String> horaFimDia = new ComboBox<>();
            
            // Adicionar horários
            for (int hora = 0; hora < 24; hora++) {
                for (int minuto = 0; minuto < 60; minuto += 60) {
                    LocalTime time = LocalTime.of(hora, minuto);
                    String horario = time.format(DateTimeFormatter.ofPattern("HH:mm"));
                    horaInicioDia.getItems().add(horario);
                    horaFimDia.getItems().add(horario);
                }
            }
            
            horaInicioDia.setValue("08:00");
            horaFimDia.setValue("18:00");
            horaInicioDia.setPrefWidth(80);
            horaFimDia.setPrefWidth(80);
            
            Label ateLabel = new Label("até");
            ateLabel.setStyle("-fx-text-fill: #666;");
            
            linha.getChildren().addAll(diaLabel, disponivelCheck, horaInicioDia, ateLabel, horaFimDia);
            calendarioSemanal.getChildren().add(linha);
            
            // Inicializar disponibilidade
            disponibilidadeSemanal.put(dia, true);
        }
    }

    private void configurarTabela() {
        colData.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getData()));
        colHoraInicio.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHoraInicio()));
        colHoraFim.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHoraFim()));
        colTempoOnline.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTempoOnline()));
        colViagensAceites.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getViagensAceites()).asObject());
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        
        tabelaHistorico.setItems(historicoDisponibilidade);
    }

    private void carregarDadosMock() {
        historicoDisponibilidade.clear();
        
        // Adicionar dados de exemplo
        historicoDisponibilidade.add(new DadosDisponibilidade("15/01/2024", "08:00", "18:00", "10h", 8, "Disponível"));
        historicoDisponibilidade.add(new DadosDisponibilidade("14/01/2024", "09:00", "17:00", "8h", 6, "Disponível"));
        historicoDisponibilidade.add(new DadosDisponibilidade("13/01/2024", "10:00", "16:00", "6h", 4, "Indisponível"));
        historicoDisponibilidade.add(new DadosDisponibilidade("12/01/2024", "08:00", "20:00", "12h", 10, "Disponível"));
        historicoDisponibilidade.add(new DadosDisponibilidade("11/01/2024", "07:00", "19:00", "12h", 9, "Disponível"));
    }

    private void atualizarStatus() {
        if (disponivel) {
            statusToggle.setText("Disponível");
            statusToggle.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;");
            statusInfo.setText("Você está atualmente disponível para receber viagens");
            statusInfo.setStyle("-fx-text-fill: #4caf50;");
        } else {
            statusToggle.setText("Indisponível");
            statusToggle.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            statusInfo.setText("Você está atualmente indisponível para receber viagens");
            statusInfo.setStyle("-fx-text-fill: #f44336;");
        }
    }

    @FXML
    private void alternarStatus() {
        disponivel = !disponivel;
        atualizarStatus();
        
        // Adicionar ao histórico
        String hoje = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String horaAtual = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        
        if (disponivel) {
            historicoDisponibilidade.add(0, new DadosDisponibilidade(hoje, horaAtual, "-", "-", 0, "Disponível"));
        } else {
            historicoDisponibilidade.add(0, new DadosDisponibilidade(hoje, "-", horaAtual, "-", 0, "Indisponível"));
        }
    }

    @FXML
    private void salvarConfiguracoes() {
        // Validar configurações
        if (horaInicio.getValue() == null || horaFim.getValue() == null) {
            mostrarAlerta("Erro", "Por favor, selecione os horários de trabalho.");
            return;
        }
        
        // Validar se hora fim é depois da hora início
        LocalTime inicio = LocalTime.parse(horaInicio.getValue());
        LocalTime fim = LocalTime.parse(horaFim.getValue());
        
        if (fim.isBefore(inicio) || fim.equals(inicio)) {
            mostrarAlerta("Erro", "A hora de fim deve ser posterior à hora de início.");
            return;
        }
        
        // Salvar configurações (aqui seria integração com backend)
        mostrarAlerta("Sucesso", "Configurações salvas com sucesso!");
        
        // Atualizar histórico
        String hoje = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        historicoDisponibilidade.add(0, new DadosDisponibilidade(hoje, horaInicio.getValue(), horaFim.getValue(), 
            calcularTempoTrabalho(inicio, fim), 0, "Configurado"));
    }

    private String calcularTempoTrabalho(LocalTime inicio, LocalTime fim) {
        long horas = java.time.Duration.between(inicio, fim).toHours();
        return horas + "h";
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static class DadosDisponibilidade {
        private String data;
        private String horaInicio;
        private String horaFim;
        private String tempoOnline;
        private int viagensAceites;
        private String status;

        public DadosDisponibilidade(String data, String horaInicio, String horaFim, String tempoOnline, int viagensAceites, String status) {
            this.data = data;
            this.horaInicio = horaInicio;
            this.horaFim = horaFim;
            this.tempoOnline = tempoOnline;
            this.viagensAceites = viagensAceites;
            this.status = status;
        }

        public String getData() { return data; }
        public String getHoraInicio() { return horaInicio; }
        public String getHoraFim() { return horaFim; }
        public String getTempoOnline() { return tempoOnline; }
        public int getViagensAceites() { return viagensAceites; }
        public String getStatus() { return status; }
    }
} 