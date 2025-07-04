package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Cliente;
import com.example.projeto2.base.service.ClienteService;
// Example imports for a Spring controller
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;


@RestController
//localhost:8080/clientes
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Listar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        try {
            return ResponseEntity.ok(clienteService.listarTodos());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    //Listar um cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.procurarPorId(id);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    //Criar um novo cliente
    @PostMapping("/add")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.guardar(cliente);
            return ResponseEntity.ok(novoCliente);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    //Atualizar um cliente pelo id
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        try {
            Cliente cliente = clienteService.atualizar(id, clienteAtualizado);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

    //Apagar um cliente pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarCliente(@PathVariable Long id) {
        try {
            if (clienteService.deletar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).build();
        }
    }

}