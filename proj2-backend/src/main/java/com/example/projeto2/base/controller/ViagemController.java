package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Viagem;
import com.example.projeto2.base.service.ViagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

    private final ViagemService viagemService;

    public ViagemController(ViagemService viagemService) {
        this.viagemService = viagemService;
    }

    // Listar todas as viagens
    @GetMapping
    public ResponseEntity<List<Viagem>> listarViagens() {
        return ResponseEntity.ok(viagemService.listarTodos());
    }

    // Buscar viagem por id
    @GetMapping("/{id}")
    public ResponseEntity<Viagem> buscarViagem(@PathVariable Long id) {
        return viagemService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar uma nova viagem
    @PostMapping
    public ResponseEntity<Viagem> criarViagem(@RequestBody Viagem viagem) {
        Viagem novaViagem = viagemService.guardar(viagem);
        return ResponseEntity.ok(novaViagem);
    }

    // Atualizar uma viagem
    @PutMapping("/{id}")
    public ResponseEntity<Viagem> atualizarViagem(@PathVariable Long id, @RequestBody Viagem viagemAtualizada) {
        return viagemService.atualizar(id, viagemAtualizada)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Apagar uma viagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarViagem(@PathVariable Long id) {
        if (viagemService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
