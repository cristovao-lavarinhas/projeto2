package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Viatura;
import com.example.projeto2.base.service.ViaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(viaturaService.listarTodos());
    }

    // Buscar viatura por id
    @GetMapping("/{id}")
    public ResponseEntity<Viatura> buscarViatura(@PathVariable Long id) {
        return viaturaService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar uma nova viatura
    @PostMapping
    public ResponseEntity<Viatura> criarViatura(@RequestBody Viatura viatura) {
        Viatura novaViatura = viaturaService.guardar(viatura);
        return ResponseEntity.ok(novaViatura);
    }

    // Atualizar uma viatura
    @PutMapping("/{id}")
    public ResponseEntity<Viatura> atualizarViatura(@PathVariable Long id, @RequestBody Viatura viaturaAtualizada) {
        return viaturaService.atualizar(id, viaturaAtualizada)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Apagar uma viatura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarViatura(@PathVariable Long id) {
        if (viaturaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

