package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Motorista;
import com.example.projeto2.base.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoristaService {
    @Autowired
    private MotoristaRepository motoristaRepository;

    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    public Motorista procurarPorId(Long id) {
        return motoristaRepository.findById(id).orElse(null);
    }

    public Motorista guardar(Motorista motorista) {
        return motoristaRepository.save(motorista);
    }

    public Motorista atualizar(Long id, Motorista motoristaAtualizado) {
        Motorista motorista = motoristaRepository.findById(id).orElse(null);
        if (motorista != null) {
            motorista.setNome(motoristaAtualizado.getNome());
            motorista.setTel(motoristaAtualizado.getTel());
            motorista.setCartaConducao(motoristaAtualizado.getCartaConducao());
            motorista.setDtNascimento(motoristaAtualizado.getDtNascimento());
            motorista.setEstado(motoristaAtualizado.getEstado());
            motorista.setEmail(motoristaAtualizado.getEmail());
            motorista.setMorada(motoristaAtualizado.getMorada());
            motorista.setIban(motoristaAtualizado.getIban());
            return motoristaRepository.save(motorista);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (motoristaRepository.existsById(id)) {
            motoristaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Motorista> listarPorEstado(String estado) {
        return motoristaRepository.findAll().stream()
            .filter(m -> estado.equalsIgnoreCase(m.getEstado()))
            .toList();
    }

    public void atualizarEstado(Long idMotorista, String novoEstado) {
        Motorista motorista = motoristaRepository.findById(idMotorista).orElseThrow();
        motorista.setEstado(novoEstado);
        motoristaRepository.save(motorista);
    }
}