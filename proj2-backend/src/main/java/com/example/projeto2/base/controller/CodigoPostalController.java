package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.CodigoPostal;
import com.example.projeto2.base.service.CodigoPostalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CodigoPostal>> listarCodPostais() {
        return ResponseEntity.ok(codigoPostalService.listarTodos());
    }

    // Buscar código postal por id
    @GetMapping("/{id}")
    public ResponseEntity<CodigoPostal> buscarCodPostal(@PathVariable Long id) {
        return codigoPostalService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo código postal
    @PostMapping
    public ResponseEntity<CodigoPostal> criarCodPostal(@RequestBody CodigoPostal codigoPostal) {
        CodigoPostal novoCodigoPostal = codigoPostalService.guardar(codigoPostal);
        return ResponseEntity.ok(novoCodigoPostal);
    }

    // Atualizar um código postal
    @PutMapping("/{id}")
    public ResponseEntity<CodigoPostal> atualizarCodPostal(@PathVariable Long id, @RequestBody CodigoPostal codigoPostalAtualizado) {
        return codigoPostalService.atualizar(id, codigoPostalAtualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Apagar um código postal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarCodPostal(@PathVariable Long id) {
        if (codigoPostalService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

