package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Motorista;
import com.example.projeto2.base.service.MotoristaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    private final MotoristaService motoristaService;

    public MotoristaController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    // Listar todos os motoristas
    @GetMapping
    public ResponseEntity<List<Motorista>> listarMotoristas() {
        return ResponseEntity.ok(motoristaService.listarTodos());
    }

    // Buscar motorista por id
    @GetMapping("/{id}")
    public ResponseEntity<Motorista> buscarMotorista(@PathVariable Long id) {
        Motorista motorista = motoristaService.procurarPorId(id);
        if (motorista != null) {
            return ResponseEntity.ok(motorista);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar um novo motorista
    @PostMapping
    public ResponseEntity<Motorista> criarMotorista(@RequestBody Motorista motorista) {
        Motorista novoMotorista = motoristaService.guardar(motorista);
        return ResponseEntity.ok(novoMotorista);
    }

    // Atualizar um motorista
    @PutMapping("/{id}")
    public ResponseEntity<Motorista> atualizarMotorista(@PathVariable Long id, @RequestBody Motorista motoristaAtualizado) {
        Motorista motorista = motoristaService.atualizar(id, motoristaAtualizado);
        if (motorista != null) {
            return ResponseEntity.ok(motorista);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Apagar um motorista
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarMotorista(@PathVariable Long id) {
        if (motoristaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
