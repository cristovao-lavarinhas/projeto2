package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPagamento", nullable = false)
    private Long idPagamento;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "idViagem", nullable = false)
    private Viagem viagem;

    @ManyToOne
    @JoinColumn(name = "idTipoPagamento", nullable = false)
    private TipoPagamento tipoPagamento;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    public Pagamento() {}

    public Pagamento(BigDecimal valor, Viagem viagem, TipoPagamento tipoPagamento, Cliente cliente) {
        this.valor = valor;
        this.viagem = viagem;
        this.tipoPagamento = tipoPagamento;
        this.cliente = cliente;
    }

    public Long getIdPagamento() { return idPagamento; }
    public void setIdPagamento(Long idPagamento) { this.idPagamento = idPagamento; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Viagem getViagem() { return viagem; }
    public void setViagem(Viagem viagem) { this.viagem = viagem; }

    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(TipoPagamento tipoPagamento) { this.tipoPagamento = tipoPagamento; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return "Pagamento{" +
                "idPagamento=" + idPagamento +
                ", valor=" + valor +
                '}';
    }
}
