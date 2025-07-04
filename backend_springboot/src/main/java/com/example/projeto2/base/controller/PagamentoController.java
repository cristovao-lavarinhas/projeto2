package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Pagamento;
import com.example.projeto2.base.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Listar todos os pagamentos
    @GetMapping
    public ResponseEntity<List<Pagamento>> listarPagamentos() {
        try {
            return ResponseEntity.ok(pagamentoService.listarTodos());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Buscar pagamento por id
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPagamento(@PathVariable Long id) {
        try {
            Pagamento pagamento = pagamentoService.procurarPorId(id);
            if (pagamento != null) {
                return ResponseEntity.ok(pagamento);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Criar um novo pagamento
    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@RequestBody Pagamento pagamento) {
        try {
            Pagamento novoPagamento = pagamentoService.guardar(pagamento);
            return ResponseEntity.ok(novoPagamento);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Atualizar um pagamento
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Long id, @RequestBody Pagamento pagamentoAtualizado) {
        try {
            Pagamento pagamento = pagamentoService.atualizar(id, pagamentoAtualizado);
            if (pagamento != null) {
                return ResponseEntity.ok(pagamento);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Apagar um pagamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarPagamento(@PathVariable Long id) {
        try {
            if (pagamentoService.deletar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
