package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Pagamento;
import com.example.projeto2.base.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    // Buscar pagamento por id
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPagamento(@PathVariable Long id) {
        return pagamentoService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo pagamento
    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@RequestBody Pagamento pagamento) {
        Pagamento novoPagamento = pagamentoService.guardar(pagamento);
        return ResponseEntity.ok(novoPagamento);
    }

    // Atualizar um pagamento
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Long id, @RequestBody Pagamento pagamentoAtualizado) {
        return pagamentoService.atualizar(id, pagamentoAtualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Apagar um pagamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarPagamento(@PathVariable Long id) {
        if (pagamentoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
