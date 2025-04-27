package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Motorista;
import com.example.projeto2.base.repository.MotoristaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;

    public MotoristaService(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    // Listar todos os motoristas
    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    // Procurar motorista por ID
    public Optional<Motorista> procurarPorId(Long id) {
        return motoristaRepository.findById(id);
    }

    // Criar ou salvar um novo motorista
    public Motorista guardar(Motorista motorista) {
        return motoristaRepository.save(motorista);
    }

    // Atualizar motorista existente
    public Optional<Motorista> atualizar(Long id, Motorista motoristaAtualizado) {
        return motoristaRepository.findById(id).map(motorista -> {
            motorista.setNome(motoristaAtualizado.getNome());
            motorista.setTel(motoristaAtualizado.getTel());
            motorista.setCartaConducao(motoristaAtualizado.getCartaConducao());
            motorista.setDtNascimento(motoristaAtualizado.getDtNascimento());
            motorista.setAvaliacao(motoristaAtualizado.getAvaliacao());
            return motoristaRepository.save(motorista);
        });
    }

    // Deletar um motorista
    public boolean deletar(Long id) {
        return motoristaRepository.findById(id).map(motorista -> {
            motoristaRepository.delete(motorista);
            return true;
        }).orElse(false);
    }
}
