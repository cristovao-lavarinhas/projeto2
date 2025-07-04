package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Avaliacao;
import com.example.projeto2.base.service.AvaliacaoService;
import java.io.IOException;
import java.util.List;
// Example imports for a Spring controller
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    // Listar todas as avaliações
    @GetMapping
    public ResponseEntity<List<Avaliacao>> listarAvaliacoes() {
        return ResponseEntity.ok(avaliacaoService.listarTodos());
    }

    // Buscar avaliação por id
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarAvaliacao(@PathVariable Long id) {
        return avaliacaoService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar uma nova avaliação
    @PostMapping
    public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
        Avaliacao novaAvaliacao = avaliacaoService.guardar(avaliacao);
        return ResponseEntity.ok(novaAvaliacao);
    }

    // Atualizar avaliação por id
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Long id, @RequestBody Avaliacao avaliacaoAtualizada) {
        return avaliacaoService.atualizar(id, avaliacaoAtualizada)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Apagar uma avaliação por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAvaliacao(@PathVariable Long id) {
        if (avaliacaoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

