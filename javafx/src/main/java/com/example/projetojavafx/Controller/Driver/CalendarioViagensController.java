package com.example.projetojavafx.Controller.Driver;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CalendarioViagensController {

    @FXML private Label mesAnoLabel;
    @FXML private VBox calendarioGrid;
    @FXML private VBox detalhesViagem;
    @FXML private VBox detalhesContent;

    private YearMonth anoMesAtual;
    private LocalDate dataSelecionada;
    private Map<LocalDate, List<ViagemCalendario>> viagensPorData = new HashMap<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("pt", "PT"));

    @FXML
    public void initialize() {
        try {
            anoMesAtual = YearMonth.now();
            carregarDadosMock();
            atualizarCalendario();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar calendário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarDadosMock() {
        viagensPorData.clear();
        
        // Adicionar algumas viagens de exemplo
        LocalDate hoje = LocalDate.now();
        LocalDate amanha = hoje.plusDays(1);
        LocalDate proximaSemana = hoje.plusDays(7);
        
        viagensPorData.put(hoje, Arrays.asList(
            new ViagemCalendario("Porto → Lisboa", "09:00", "ATIVA", "Viagem em andamento"),
            new ViagemCalendario("Lisboa → Faro", "14:30", "AGENDADA", "Viagem agendada")
        ));
        
        viagensPorData.put(amanha, Arrays.asList(
            new ViagemCalendario("Faro → Porto", "08:00", "AGENDADA", "Viagem agendada")
        ));
        
        viagensPorData.put(proximaSemana, Arrays.asList(
            new ViagemCalendario("Porto → Braga", "10:00", "AGENDADA", "Viagem agendada"),
            new ViagemCalendario("Braga → Guimarães", "15:00", "AGENDADA", "Viagem agendada")
        ));
        
        // Adicionar algumas viagens concluídas
        LocalDate ontem = hoje.minusDays(1);
        viagensPorData.put(ontem, Arrays.asList(
            new ViagemCalendario("Lisboa → Porto", "16:00", "CONCLUIDA", "Viagem concluída")
        ));
    }

    private void atualizarCalendario() {
        try {
            mesAnoLabel.setText(anoMesAtual.format(formatter));
            calendarioGrid.getChildren().clear();
            
            LocalDate primeiroDia = anoMesAtual.atDay(1);
            LocalDate ultimoDia = anoMesAtual.atEndOfMonth();
            
            // Ajustar para começar no domingo
            int diaSemana = primeiroDia.getDayOfWeek().getValue();
            int diasParaSubtrair = diaSemana == 7 ? 0 : diaSemana; // Domingo = 7, mas queremos 0
            LocalDate primeiroDiaSemana = primeiroDia.minusDays(diasParaSubtrair);
            
            // Criar semanas (máximo 6 semanas para evitar loop infinito)
            LocalDate dataAtual = primeiroDiaSemana;
            int semanasCriadas = 0;
            
            while (semanasCriadas < 6 && (!dataAtual.isAfter(ultimoDia) || dataAtual.getDayOfWeek().getValue() != 7)) {
                HBox semana = new HBox(2);
                semana.setAlignment(Pos.CENTER);
                
                for (int i = 0; i < 7; i++) {
                    VBox dia = criarDiaCalendario(dataAtual);
                    semana.getChildren().add(dia);
                    dataAtual = dataAtual.plusDays(1);
                }
                
                calendarioGrid.getChildren().add(semana);
                semanasCriadas++;
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar calendário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private VBox criarDiaCalendario(LocalDate data) {
        VBox dia = new VBox(2);
        dia.setAlignment(Pos.TOP_CENTER);
        dia.setPrefSize(120, 100);
        dia.setMaxSize(120, 100);
        dia.setPadding(new Insets(5));
        
        // Estilo base
        String estiloBase = "-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1;";
        
        // Verificar se é do mês atual
        boolean isMesAtual = data.getMonth() == anoMesAtual.getMonth();
        boolean isHoje = data.equals(LocalDate.now());
        boolean isSelecionado = data.equals(dataSelecionada);
        
        // Aplicar estilos
        if (isHoje) {
            estiloBase += " -fx-background-color: #e3f2fd; -fx-border-color: #2196f3; -fx-border-width: 2;";
        } else if (isSelecionado) {
            estiloBase += " -fx-background-color: #f3e5f5; -fx-border-color: #9c27b0; -fx-border-width: 2;";
        } else if (!isMesAtual) {
            estiloBase += " -fx-background-color: #f5f5f5; -fx-text-fill: #999;";
        }
        
        dia.setStyle(estiloBase);
        
        // Número do dia
        Label numeroDia = new Label(String.valueOf(data.getDayOfMonth()));
        numeroDia.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        if (!isMesAtual) {
            numeroDia.setStyle("-fx-text-fill: #999; -fx-font-weight: bold; -fx-font-size: 14px;");
        }
        
        // Indicadores de viagens
        VBox indicadores = new VBox(1);
        indicadores.setAlignment(Pos.CENTER);
        
        List<ViagemCalendario> viagens = viagensPorData.get(data);
        if (viagens != null && !viagens.isEmpty()) {
            for (ViagemCalendario viagem : viagens) {
                Region indicador = new Region();
                indicador.setPrefSize(8, 8);
                indicador.setMaxSize(8, 8);
                
                switch (viagem.getStatus()) {
                    case "ATIVA":
                        indicador.setStyle("-fx-background-color: #4caf50; -fx-background-radius: 4;");
                        break;
                    case "AGENDADA":
                        indicador.setStyle("-fx-background-color: #ff9800; -fx-background-radius: 4;");
                        break;
                    case "CONCLUIDA":
                        indicador.setStyle("-fx-background-color: #2196f3; -fx-background-radius: 4;");
                        break;
                }
                
                indicadores.getChildren().add(indicador);
            }
        }
        
        dia.getChildren().addAll(numeroDia, indicadores);
        
        // Evento de clique
        dia.setOnMouseClicked(e -> {
            dataSelecionada = data;
            mostrarDetalhesViagem(data);
            atualizarCalendario(); // Atualizar seleção visual
        });
        
        return dia;
    }

    private void mostrarDetalhesViagem(LocalDate data) {
        List<ViagemCalendario> viagens = viagensPorData.get(data);
        
        if (viagens != null && !viagens.isEmpty()) {
            detalhesContent.getChildren().clear();
            
            for (ViagemCalendario viagem : viagens) {
                VBox viagemCard = criarCardViagem(viagem);
                detalhesContent.getChildren().add(viagemCard);
            }
            
            detalhesViagem.setVisible(true);
        } else {
            detalhesViagem.setVisible(false);
        }
    }

    private VBox criarCardViagem(ViagemCalendario viagem) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");
        
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label titulo = new Label(viagem.getTitulo());
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        
        Label hora = new Label(viagem.getHora());
        hora.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
        
        Region status = new Region();
        status.setPrefSize(12, 12);
        status.setMaxSize(12, 12);
        
        switch (viagem.getStatus()) {
            case "ATIVA":
                status.setStyle("-fx-background-color: #4caf50; -fx-background-radius: 6;");
                break;
            case "AGENDADA":
                status.setStyle("-fx-background-color: #ff9800; -fx-background-radius: 6;");
                break;
            case "CONCLUIDA":
                status.setStyle("-fx-background-color: #2196f3; -fx-background-radius: 6;");
                break;
        }
        
        header.getChildren().addAll(status, titulo, hora);
        
        Label descricao = new Label(viagem.getDescricao());
        descricao.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");
        
        card.getChildren().addAll(header, descricao);
        
        return card;
    }

    @FXML
    private void irParaHoje() {
        anoMesAtual = YearMonth.now();
        dataSelecionada = LocalDate.now();
        atualizarCalendario();
        mostrarDetalhesViagem(dataSelecionada);
    }

    @FXML
    private void mesAnterior() {
        anoMesAtual = anoMesAtual.minusMonths(1);
        atualizarCalendario();
    }

    @FXML
    private void proximoMes() {
        anoMesAtual = anoMesAtual.plusMonths(1);
        atualizarCalendario();
    }

    private static class ViagemCalendario {
        private String titulo;
        private String hora;
        private String status;
        private String descricao;

        public ViagemCalendario(String titulo, String hora, String status, String descricao) {
            this.titulo = titulo;
            this.hora = hora;
            this.status = status;
            this.descricao = descricao;
        }

        public String getTitulo() { return titulo; }
        public String getHora() { return hora; }
        public String getStatus() { return status; }
        public String getDescricao() { return descricao; }
    }
} 