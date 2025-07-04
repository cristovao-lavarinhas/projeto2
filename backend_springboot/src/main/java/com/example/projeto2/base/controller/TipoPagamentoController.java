package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.TipoPagamento;
import com.example.projeto2.base.service.TipoPagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        try {
            return ResponseEntity.ok(tipoPagamentoService.listarTodos());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Buscar um tipo de pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoPagamento> buscarTipoPagamento(@PathVariable Long id) {
        try {
            TipoPagamento tipoPagamento = tipoPagamentoService.procurarPorId(id);
            if (tipoPagamento != null) {
                return ResponseEntity.ok(tipoPagamento);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Criar um novo tipo de pagamento
    @PostMapping("/add")
    public ResponseEntity<TipoPagamento> criarTipoPagamento(@RequestBody TipoPagamento tipoPagamento) {
        try {
            TipoPagamento novoTipoPagamento = tipoPagamentoService.guardar(tipoPagamento);
            return ResponseEntity.ok(novoTipoPagamento);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Atualizar um tipo de pagamento existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoPagamento> atualizarTipoPagamento(@PathVariable Long id,
                                                                @RequestBody TipoPagamento tipoPagamentoAtualizado) {
        try {
            TipoPagamento tipoPagamento = tipoPagamentoService.atualizar(id, tipoPagamentoAtualizado);
            if (tipoPagamento != null) {
                return ResponseEntity.ok(tipoPagamento);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Apagar um tipo de pagamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarTipoPagamento(@PathVariable Long id) {
        try {
            if (tipoPagamentoService.deletar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
