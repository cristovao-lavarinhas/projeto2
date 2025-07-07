package com.example.projetojavafx.Controller.Driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AlertasController {

    @FXML private VBox alertasAtivosContainer;
    @FXML private CheckBox notifNovaViagem;
    @FXML private CheckBox notifViagemAceite;
    @FXML private CheckBox notifViagemCancelada;
    @FXML private CheckBox notifLembreteViagem;
    @FXML private CheckBox notifPagamentoRecebido;
    @FXML private CheckBox notifPagamentoPendente;
    @FXML private CheckBox notifBonusDisponivel;
    @FXML private CheckBox notifDocumentoVencendo;
    @FXML private CheckBox notifDocumentoVencido;
    @FXML private CheckBox notifManutencaoViatura;
    @FXML private CheckBox notifNovaAvaliacao;
    @FXML private CheckBox notifAvaliacaoBaixa;
    @FXML private CheckBox notifMensagemSuporte;
    @FXML private CheckBox notifTicketResolvido;
    @FXML private CheckBox notifManutencaoSistema;
    @FXML private CheckBox notifAtualizacaoApp;
    @FXML private ComboBox<String> somNotificacao;
    @FXML private Slider volumeSlider;
    @FXML private Label volumeLabel;
    @FXML private CheckBox vibracaoCheckbox;
    @FXML private CheckBox horariosSilencioCheckbox;
    @FXML private ComboBox<String> horaSilencioInicio;
    @FXML private ComboBox<String> horaSilencioFim;
    @FXML private TableView<NotificacaoHistorico> tabelaHistorico;
    @FXML private TableColumn<NotificacaoHistorico, String> colDataHora;
    @FXML private TableColumn<NotificacaoHistorico, String> colTipo;
    @FXML private TableColumn<NotificacaoHistorico, String> colMensagem;
    @FXML private TableColumn<NotificacaoHistorico, String> colStatus;
    @FXML private TableColumn<NotificacaoHistorico, String> colAcoes;
    @FXML private Label contadorNotificacoes;

    private ObservableList<NotificacaoHistorico> historicoNotificacoes = FXCollections.observableArrayList();
    private List<AlertaAtivo> alertasAtivos = new ArrayList<>();

    @FXML
    public void initialize() {
        try {
            configurarComboBoxes();
            configurarSlider();
            configurarTabela();
            carregarDadosMock();
            carregarAlertasAtivos();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar alertas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarComboBoxes() {
        // Sons de notificação
        somNotificacao.getItems().addAll("Padrão", "Ding", "Bell", "Chime", "Notification", "Silencioso");
        somNotificacao.setValue("Padrão");
        
        // Horários de silêncio
        List<String> horarios = new ArrayList<>();
        for (int hora = 0; hora < 24; hora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                String horario = String.format("%02d:%02d", hora, minuto);
                horarios.add(horario);
            }
        }
        
        horaSilencioInicio.getItems().addAll(horarios);
        horaSilencioFim.getItems().addAll(horarios);
        horaSilencioInicio.setValue("22:00");
        horaSilencioFim.setValue("08:00");
    }

    private void configurarSlider() {
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            volumeLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
        });
    }

    private void configurarTabela() {
        colDataHora.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDataHora()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));
        colMensagem.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMensagem()));
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        colAcoes.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("Ver"));
        
        tabelaHistorico.setItems(historicoNotificacoes);
    }

    private void carregarDadosMock() {
        historicoNotificacoes.clear();
        
        // Adicionar notificações de exemplo
        historicoNotificacoes.add(new NotificacaoHistorico("15/01/2024 14:30", "Viagem", "Nova viagem disponível para Lisboa", "Lida"));
        historicoNotificacoes.add(new NotificacaoHistorico("15/01/2024 12:15", "Pagamento", "Pagamento recebido: €25,00", "Lida"));
        historicoNotificacoes.add(new NotificacaoHistorico("15/01/2024 10:00", "Documento", "Licença de condução vence em 30 dias", "Não lida"));
        historicoNotificacoes.add(new NotificacaoHistorico("14/01/2024 18:45", "Avaliação", "Nova avaliação de 5 estrelas", "Lida"));
        historicoNotificacoes.add(new NotificacaoHistorico("14/01/2024 16:20", "Suporte", "Resposta do suporte disponível", "Não lida"));
        
        contadorNotificacoes.setText(historicoNotificacoes.size() + " notificações");
    }

    private void carregarAlertasAtivos() {
        alertasAtivos.clear();
        alertasAtivosContainer.getChildren().clear();
        
        // Adicionar alertas ativos
        alertasAtivos.add(new AlertaAtivo("⚠️ Documento", "Licença de condução vence em 30 dias", "Urgente", "#ff9800"));
        alertasAtivos.add(new AlertaAtivo("🔧 Manutenção", "Viatura necessita revisão", "Médio", "#2196f3"));
        alertasAtivos.add(new AlertaAtivo("💰 Bônus", "Bônus de fim de semana disponível", "Baixo", "#4caf50"));
        
        for (AlertaAtivo alerta : alertasAtivos) {
            VBox alertaCard = criarCardAlerta(alerta);
            alertasAtivosContainer.getChildren().add(alertaCard);
        }
    }

    private VBox criarCardAlerta(AlertaAtivo alerta) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: white; -fx-border-color: " + alerta.getCor() + "; -fx-border-width: 2; -fx-border-radius: 6; -fx-background-radius: 6;");
        
        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label titulo = new Label(alerta.getTitulo());
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label prioridade = new Label(alerta.getPrioridade());
        prioridade.setStyle("-fx-background-color: " + alerta.getCor() + "; -fx-text-fill: white; -fx-padding: 2 6; -fx-background-radius: 4; -fx-font-size: 10px;");
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button fechar = new Button("×");
        fechar.setStyle("-fx-background-color: transparent; -fx-text-fill: #999; -fx-font-size: 16px; -fx-font-weight: bold;");
        fechar.setOnAction(e -> removerAlerta(alerta, card));
        
        header.getChildren().addAll(titulo, prioridade, spacer, fechar);
        
        Label mensagem = new Label(alerta.getMensagem());
        mensagem.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        mensagem.setWrapText(true);
        
        card.getChildren().addAll(header, mensagem);
        
        return card;
    }

    private void removerAlerta(AlertaAtivo alerta, VBox card) {
        alertasAtivos.remove(alerta);
        alertasAtivosContainer.getChildren().remove(card);
    }

    @FXML
    private void salvarConfiguracoes() {
        // Validar configurações
        if (horariosSilencioCheckbox.isSelected()) {
            if (horaSilencioInicio.getValue() == null || horaSilencioFim.getValue() == null) {
                mostrarAlerta("Erro", "Por favor, selecione os horários de silêncio.");
                return;
            }
        }
        
        // Salvar configurações (aqui seria integração com backend)
        mostrarAlerta("Sucesso", "Configurações de alertas salvas com sucesso!");
        
        // Adicionar ao histórico
        String agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        historicoNotificacoes.add(0, new NotificacaoHistorico(agora, "Sistema", "Configurações de alertas atualizadas", "Lida"));
        contadorNotificacoes.setText(historicoNotificacoes.size() + " notificações");
    }

    @FXML
    private void limparHistorico() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Limpar Histórico");
        alert.setHeaderText("Confirmar ação");
        alert.setContentText("Deseja realmente limpar todo o histórico de notificações?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            historicoNotificacoes.clear();
            contadorNotificacoes.setText("0 notificações");
            mostrarAlerta("Sucesso", "Histórico de notificações limpo com sucesso!");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static class AlertaAtivo {
        private String titulo;
        private String mensagem;
        private String prioridade;
        private String cor;

        public AlertaAtivo(String titulo, String mensagem, String prioridade, String cor) {
            this.titulo = titulo;
            this.mensagem = mensagem;
            this.prioridade = prioridade;
            this.cor = cor;
        }

        public String getTitulo() { return titulo; }
        public String getMensagem() { return mensagem; }
        public String getPrioridade() { return prioridade; }
        public String getCor() { return cor; }
    }

    public static class NotificacaoHistorico {
        private String dataHora;
        private String tipo;
        private String mensagem;
        private String status;

        public NotificacaoHistorico(String dataHora, String tipo, String mensagem, String status) {
            this.dataHora = dataHora;
            this.tipo = tipo;
            this.mensagem = mensagem;
            this.status = status;
        }

        public String getDataHora() { return dataHora; }
        public String getTipo() { return tipo; }
        public String getMensagem() { return mensagem; }
        public String getStatus() { return status; }
    }
} 