package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Cliente;
import com.example.projeto2.base.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    //Listar um cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
        return clienteService.procurarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Criar um novo cliente
    @PostMapping("/add")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.guardar(cliente);
        return ResponseEntity.ok(novoCliente);
    }

    //Atualizar um cliente pelo id
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        return clienteService.atualizar(id, clienteAtualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    //Apagar um cliente pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarCliente(@PathVariable Long id) {
        if (clienteService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}