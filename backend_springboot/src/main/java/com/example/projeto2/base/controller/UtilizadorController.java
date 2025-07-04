// Exemplo para UtilizadorController
package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Utilizador;
import com.example.projeto2.base.repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
// Example imports for a Spring controller
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilizadores")
public class UtilizadorController {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @GetMapping
    public List<Utilizador> getAll() {
        return utilizadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Utilizador> getById(@PathVariable Long id) {
        return utilizadorRepository.findById(id);
    }

    @PostMapping
    public Utilizador create(@RequestBody Utilizador u) {
        return utilizadorRepository.save(u);
    }

    @PutMapping("/{id}")
    public Utilizador update(@PathVariable Long id, @RequestBody Utilizador u) {
        u.setId(id);
        return utilizadorRepository.save(u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        utilizadorRepository.deleteById(id);
    }
}
