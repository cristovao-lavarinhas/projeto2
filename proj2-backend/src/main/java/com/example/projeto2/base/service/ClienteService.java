package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Cliente;
import com.example.projeto2.base.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> procurarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setNif(clienteAtualizado.getNif());
            cliente.setTel(clienteAtualizado.getTel());
            cliente.setRua(clienteAtualizado.getRua());
            cliente.setNPorta(clienteAtualizado.getNPorta());
            cliente.setCodigoPostal(clienteAtualizado.getCodigoPostal());
            return clienteRepository.save(cliente);
        });
    }

    public boolean deletar(Long id) {
        return clienteRepository.findById(id).map(cliente -> {
            clienteRepository.delete(cliente);
            return true;
        }).orElse(false);
    }
}
