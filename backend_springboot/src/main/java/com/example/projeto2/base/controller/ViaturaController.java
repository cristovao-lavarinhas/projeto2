package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Viatura;
import com.example.projeto2.base.service.ViaturaService;
import org.springframework.http.ResponseEntity;
// Example imports for a Spring controller
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/viaturas")
public class ViaturaController {

    private final ViaturaService viaturaService;

    public ViaturaController(ViaturaService viaturaService) {
        this.viaturaService = viaturaService;
    }

    // Listar todas as viaturas
    @GetMapping
    public ResponseEntity<List<Viatura>> listarViaturas() {
        try {
            return ResponseEntity.ok(viaturaService.listarTodos());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Buscar viatura por id
    @GetMapping("/{id}")
    public ResponseEntity<Viatura> buscarViatura(@PathVariable Long id) {
        try {
            Viatura viatura = viaturaService.procurarPorId(id);
            if (viatura != null) {
                return ResponseEntity.ok(viatura);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Criar uma nova viatura
    @PostMapping
    public ResponseEntity<Viatura> criarViatura(@RequestBody Viatura viatura) {
        try {
            Viatura novaViatura = viaturaService.guardar(viatura);
            return ResponseEntity.ok(novaViatura);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Atualizar uma viatura
    @PutMapping("/{id}")
    public ResponseEntity<Viatura> atualizarViatura(@PathVariable Long id, @RequestBody Viatura viaturaAtualizada) {
        try {
            Viatura viatura = viaturaService.atualizar(id, viaturaAtualizada);
            if (viatura != null) {
                return ResponseEntity.ok(viatura);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Apagar uma viatura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarViatura(@PathVariable Long id) {
        try {
            if (viaturaService.deletar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }
}

