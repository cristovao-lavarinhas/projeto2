package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Viagem;
import com.example.projeto2.base.service.ViagemService;
import org.springframework.http.ResponseEntity;
// Example imports for a Spring controller
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
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
        try {
            return ResponseEntity.ok(viagemService.listarTodos());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Buscar viagem por id
    @GetMapping("/{id}")
    public ResponseEntity<Viagem> buscarViagem(@PathVariable Long id) {
        try {
            Viagem viagem = viagemService.procurarPorId(id);
            if (viagem != null) {
                return ResponseEntity.ok(viagem);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Criar uma nova viagem
    @PostMapping
    public ResponseEntity<Viagem> criarViagem(@RequestBody Viagem viagem) {
        try {
            Viagem novaViagem = viagemService.guardar(viagem);
            return ResponseEntity.ok(novaViagem);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Atualizar uma viagem
    @PutMapping("/{id}")
    public ResponseEntity<Viagem> atualizarViagem(@PathVariable Long id, @RequestBody Viagem viagemAtualizada) {
        try {
            Viagem viagem = viagemService.atualizar(id, viagemAtualizada);
            if (viagem != null) {
                return ResponseEntity.ok(viagem);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Apagar uma viagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarViagem(@PathVariable Long id) {
        try {
            if (viagemService.deletar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
