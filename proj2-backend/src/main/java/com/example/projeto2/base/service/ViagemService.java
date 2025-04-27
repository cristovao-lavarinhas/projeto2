package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Viagem;
import com.example.projeto2.base.repository.ViagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViagemService {

    private final ViagemRepository viagemRepository;

    public ViagemService(ViagemRepository viagemRepository) {
        this.viagemRepository = viagemRepository;
    }

    // Listar todas as viagens
    public List<Viagem> listarTodos() {
        return viagemRepository.findAll();
    }

    // Procurar viagem por ID
    public Optional<Viagem> procurarPorId(Long id) {
        return viagemRepository.findById(id);
    }

    // Criar ou salvar uma nova viagem
    public Viagem guardar(Viagem viagem) {
        return viagemRepository.save(viagem);
    }

    // Atualizar viagem existente
    public Optional<Viagem> atualizar(Long id, Viagem viagemAtualizada) {
        return viagemRepository.findById(id).map(viagem -> {
            viagem.setData(viagemAtualizada.getData());
            viagem.setHoraInicio(viagemAtualizada.getHoraInicio());
            viagem.setHoraFim(viagemAtualizada.getHoraFim());
            viagem.setPreco(viagemAtualizada.getPreco());
            viagem.setMotorista(viagemAtualizada.getMotorista());
            viagem.setCliente(viagemAtualizada.getCliente());
            return viagemRepository.save(viagem);
        });
    }

    // Deletar uma viagem
    public boolean deletar(Long id) {
        return viagemRepository.findById(id).map(viagem -> {
            viagemRepository.delete(viagem);
            return true;
        }).orElse(false);
    }
}

