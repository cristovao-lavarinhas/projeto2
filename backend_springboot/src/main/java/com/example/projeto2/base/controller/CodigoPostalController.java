package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.CodigoPostal;
import com.example.projeto2.base.service.CodigoPostalService;
// Example imports for a Spring controller
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/codPostais")
public class CodigoPostalController {

    private final CodigoPostalService codigoPostalService;

    public CodigoPostalController(CodigoPostalService codigoPostalService) {
        this.codigoPostalService = codigoPostalService;
    }

    // Listar todos os códigos postais
    @GetMapping
    public ResponseEntity<Object> listarCodPostais() {
        try {
            return ResponseEntity.ok(codigoPostalService.listarTodos());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Buscar código postal por id
    @GetMapping("/{id}")
    public ResponseEntity<CodigoPostal> buscarCodPostal(@PathVariable Long id) {
        try {
            CodigoPostal codigoPostal = codigoPostalService.procurarPorId(id);
            if (codigoPostal != null) {
                return ResponseEntity.ok(codigoPostal);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Criar um novo código postal
    @PostMapping
    public ResponseEntity<CodigoPostal> criarCodPostal(@RequestBody CodigoPostal codigoPostal) {
        try {
            CodigoPostal novoCodigoPostal = codigoPostalService.guardar(codigoPostal);
            return ResponseEntity.ok(novoCodigoPostal);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Atualizar um código postal
    @PutMapping("/{id}")
    public ResponseEntity<CodigoPostal> atualizarCodPostal(@PathVariable Long id, @RequestBody CodigoPostal codigoPostalAtualizado) {
        try {
            CodigoPostal codigoPostal = codigoPostalService.atualizar(id, codigoPostalAtualizado);
            if (codigoPostal != null) {
                return ResponseEntity.ok(codigoPostal);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Apagar um código postal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarCodPostal(@PathVariable Long id) {
        try {
            if (codigoPostalService.deletar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }
}

