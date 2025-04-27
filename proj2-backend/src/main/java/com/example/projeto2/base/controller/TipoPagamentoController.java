package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.TipoPagamento;
import com.example.projeto2.base.service.TipoPagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-pagamentos")
public class TipoPagamentoController {

    private final TipoPagamentoService tipoPagamentoService;

    public TipoPagamentoController(TipoPagamentoService tipoPagamentoService) {
        this.tipoPagamentoService = tipoPagamentoService;
    }

    // Listar todos os tipos de pagamento
    @GetMapping
    public ResponseEntity<List<TipoPagamento>> listarTipoPagamentos() {
        return ResponseEntity.ok(tipoPagamentoService.listarTodos());
    }

    // Buscar um tipo de pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoPagamento> buscarTipoPagamento(@PathVariable Long id) {
        return tipoPagamentoService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo tipo de pagamento
    @PostMapping("/add")
    public ResponseEntity<TipoPagamento> criarTipoPagamento(@RequestBody TipoPagamento tipoPagamento) {
        TipoPagamento novoTipoPagamento = tipoPagamentoService.guardar(tipoPagamento);
        return ResponseEntity.ok(novoTipoPagamento);
    }

    // Atualizar um tipo de pagamento existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoPagamento> atualizarTipoPagamento(@PathVariable Long id,
                                                                @RequestBody TipoPagamento tipoPagamentoAtualizado) {
        return tipoPagamentoService.atualizar(id, tipoPagamentoAtualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Apagar um tipo de pagamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarTipoPagamento(@PathVariable Long id) {
        if (tipoPagamentoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
