package com.example.projetojavafx.Controller.Driver;

import java.awt.Desktop;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

public class VerPagamentosController {

    @FXML private TableView<Pagamento> pagamentosTable;
    @FXML private TableColumn<Pagamento, String> dataColumn;
    @FXML private TableColumn<Pagamento, String> valorColumn;
    @FXML private TableColumn<Pagamento, String> estadoColumn;
    @FXML private TableColumn<Pagamento, String> referenciaColumn;
    @FXML private TableColumn<Pagamento, String> descricaoColumn;
    @FXML private TableColumn<Pagamento, Void> detalhesColumn;
    @FXML private ComboBox<String> estadoFilterCombo;
    @FXML private Label feedbackLabel;
    @FXML private Label totalRecebidoLabel;
    @FXML private Label totalPendenteLabel;
    @FXML private Button exportarPdfButton;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private Button downloadReciboButton;

    private ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        referenciaColumn.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Mock de pagamentos
        pagamentos.addAll(
            new Pagamento("2024-06-01", "25,00€", "Pago", "123456", "Viagem Porto"),
            new Pagamento("2024-06-03", "18,50€", "Pendente", "123457", "Viagem Faro"),
            new Pagamento("2024-06-05", "30,00€", "Pago", "123458", "Viagem Lisboa"),
            new Pagamento("2024-06-07", "12,00€", "Recusado", "123459", "Viagem Coimbra")
        );
        pagamentosTable.setItems(pagamentos);
        atualizarTotais();

        // Filtro de estado
        estadoFilterCombo.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente", "Recusado"));
        estadoFilterCombo.getSelectionModel().selectFirst();
        estadoFilterCombo.setOnAction(e -> pesquisar());

        // Feedback label
        feedbackLabel.setVisible(false);

        // Coluna detalhes
        detalhesColumn.setCellFactory(col -> new TableCell<Pagamento, Void>() {
            private final Button btn = new Button("Ver detalhes");
            {
                btn.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #333; -fx-background-radius: 8;");
                btn.setOnAction(e -> {
                    Pagamento pagamento = getTableView().getItems().get(getIndex());
                    mostrarDetalhesPagamento(pagamento);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        // Cores no estado
        estadoColumn.setCellFactory(column -> new TableCell<Pagamento, String>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    switch (estado.toLowerCase()) {
                        case "pago":
                            setStyle("-fx-background-color: #e6f9ea; -fx-text-fill: #219653; -fx-font-weight: bold;");
                            break;
                        case "pendente":
                            setStyle("-fx-background-color: #fffbe6; -fx-text-fill: #f2c200; -fx-font-weight: bold;");
                            break;
                        case "recusado":
                            setStyle("-fx-background-color: #fdeaea; -fx-text-fill: #d32f2f; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });

        // Ativar/desativar botões conforme seleção
        pagamentosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            boolean ativo = newSel != null;
            downloadReciboButton.setDisable(!ativo);
        });
        pagamentosTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldSel, newSel) -> {
            boolean ativo = pagamentosTable.getSelectionModel().getSelectedIndex() >= 0;
            exportarPdfButton.setDisable(!ativo && pagamentosTable.getItems().isEmpty());
        });
        exportarPdfButton.setDisable(pagamentosTable.getItems().isEmpty());
        downloadReciboButton.setDisable(true);

        // Filtro por datas
        dataInicioPicker.setOnAction(e -> pesquisar());
        dataFimPicker.setOnAction(e -> pesquisar());

        // Botão download recibo
        downloadReciboButton.setOnAction(e -> {
            Pagamento pagamento = pagamentosTable.getSelectionModel().getSelectedItem();
            if (pagamento != null) {
                // Aqui deves chamar o backend para gerar/download do recibo real
                gerarReciboPDF(pagamento);
            }
        });

        // Botão exportar PDF
        exportarPdfButton.setOnAction(e -> {
            // Aqui deves chamar o backend para gerar/exportar PDF real de todos os pagamentos visíveis
            exportarPagamentosPDF(pagamentosTable.getItems());
        });
    }

    private void pesquisar() {
        String estadoSelecionado = estadoFilterCombo != null ? estadoFilterCombo.getValue() : "Todos";
        LocalDate dataInicio = dataInicioPicker.getValue();
        LocalDate dataFim = dataFimPicker.getValue();
        if ((estadoSelecionado == null || estadoSelecionado.equals("Todos")) && dataInicio == null && dataFim == null) {
            pagamentosTable.setItems(pagamentos);
        } else {
            ObservableList<Pagamento> filtrados = pagamentos.filtered(p -> {
                boolean correspondeEstado = estadoSelecionado == null || estadoSelecionado.equals("Todos") ||
                        p.getEstado().equalsIgnoreCase(estadoSelecionado);
                boolean correspondeData = true;
                try {
                    LocalDate dataPagamento = LocalDate.parse(p.getData());
                    if (dataInicio != null && dataPagamento.isBefore(dataInicio)) correspondeData = false;
                    if (dataFim != null && dataPagamento.isAfter(dataFim)) correspondeData = false;
                } catch (Exception ignored) {}
                return correspondeEstado && correspondeData;
            });
            pagamentosTable.setItems(filtrados);
        }
        atualizarTotais();
    }

    private void mostrarFeedback(String mensagem, boolean sucesso) {
        feedbackLabel.setText(mensagem);
        feedbackLabel.setStyle(sucesso ? "-fx-text-fill: #219653; -fx-font-weight: bold;" : "-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        feedbackLabel.setVisible(true);
        new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(() -> feedbackLabel.setVisible(false));
        }).start();
    }

    private void mostrarDetalhesPagamento(Pagamento pagamento) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Pagamento");
        alert.setHeaderText("Informação do Pagamento");
        alert.setContentText(
                "Data: " + pagamento.getData() + "\n" +
                "Valor: " + pagamento.getValor() + "\n" +
                "Estado: " + pagamento.getEstado() + "\n" +
                "Referência: " + pagamento.getReferencia() + "\n" +
                "Descrição: " + pagamento.getDescricao()
        );
        alert.showAndWait();
    }

    private void atualizarTotais() {
        double totalRecebido = pagamentosTable.getItems().stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Pago"))
                .mapToDouble(p -> parseValor(p.getValor())).sum();
        double totalPendente = pagamentosTable.getItems().stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Pendente"))
                .mapToDouble(p -> parseValor(p.getValor())).sum();
        totalRecebidoLabel.setText(String.format("Total recebido: %.2f€", totalRecebido));
        totalPendenteLabel.setText(String.format("Total pendente: %.2f€", totalPendente));
    }

    private double parseValor(String valor) {
        try {
            return Double.parseDouble(valor.replace(",", ".").replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            return 0.0;
        }
    }

    // Modelo interno para exemplo
    public static class Pagamento {
        private final String data;
        private final String valor;
        private final String estado;
        private final String referencia;
        private final String descricao;

        public Pagamento(String data, String valor, String estado, String referencia, String descricao) {
            this.data = data;
            this.valor = valor;
            this.estado = estado;
            this.referencia = referencia;
            this.descricao = descricao;
        }
        public String getData() { return data; }
        public String getValor() { return valor; }
        public String getEstado() { return estado; }
        public String getReferencia() { return referencia; }
        public String getDescricao() { return descricao; }
    }

    // --- Integração futura com backend ---
    // Chamar API para buscar pagamentos reais
    private void buscarPagamentosDoBackend() {
        // Exemplo:
        // pagamentos.setAll(apiClient.getPagamentos(motoristaId, ...));
        // pesquisar();
    }

    // Gerar recibo PDF real (mock para já)
    private void gerarReciboPDF(Pagamento pagamento) {
        try {
            String fileName = "Recibo_" + pagamento.getReferencia() + ".pdf";
            File file = new File(System.getProperty("user.home"), fileName);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            document.add(new Paragraph("Recibo de Pagamento").setFont(font).setFontSize(18));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Data: " + pagamento.getData()));
            document.add(new Paragraph("Valor: " + pagamento.getValor()));
            document.add(new Paragraph("Estado: " + pagamento.getEstado()));
            document.add(new Paragraph("Referência: " + pagamento.getReferencia()));
            document.add(new Paragraph("Descrição: " + pagamento.getDescricao()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Obrigado pela sua preferência!"));
            document.close();
            mostrarFeedback("Recibo PDF gerado em " + file.getAbsolutePath(), true);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            mostrarFeedback("Erro ao gerar recibo PDF!", false);
        }
    }

    // Exportar todos os pagamentos visíveis para PDF (mock para já)
    private void exportarPagamentosPDF(List<Pagamento> pagamentos) {
        try {
            String fileName = "Pagamentos_" + java.time.LocalDate.now() + ".pdf";
            File file = new File(System.getProperty("user.home"), fileName);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            document.add(new Paragraph("Relatório de Pagamentos").setFont(font).setFontSize(18));
            document.add(new Paragraph("Data de exportação: " + java.time.LocalDate.now()));
            document.add(new Paragraph(" "));
            
            Table table = new Table(5);
            table.addCell("Data");
            table.addCell("Valor");
            table.addCell("Estado");
            table.addCell("Referência");
            table.addCell("Descrição");
            for (Pagamento p : pagamentos) {
                table.addCell(p.getData());
                table.addCell(p.getValor());
                table.addCell(p.getEstado());
                table.addCell(p.getReferencia());
                table.addCell(p.getDescricao());
            }
            document.add(table);
            document.close();
            mostrarFeedback("PDF exportado em " + file.getAbsolutePath(), true);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            mostrarFeedback("Erro ao exportar PDF!", false);
        }
    }
}
