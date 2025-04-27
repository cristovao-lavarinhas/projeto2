package com.example.projeto2.base.service;


import com.example.projeto2.base.model.Avaliacao;
import com.example.projeto2.base.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    // Listar todas as avaliações
    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    // Procurar avaliação por ID
    public Optional<Avaliacao> procurarPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    // Criar ou salvar uma nova avaliação
    public Avaliacao guardar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    // Atualizar avaliação existente
    public Optional<Avaliacao> atualizar(Long id, Avaliacao avaliacaoAtualizada) {
        return avaliacaoRepository.findById(id).map(avaliacao -> {
            avaliacao.setNota(avaliacaoAtualizada.getNota());
            avaliacao.setComentario(avaliacaoAtualizada.getComentario());
            avaliacao.setClientes(avaliacaoAtualizada.getClientes());
            avaliacao.setMotoristas(avaliacaoAtualizada.getMotoristas());
            return avaliacaoRepository.save(avaliacao);
        });
    }

    // Deletar avaliação
    public boolean deletar(Long id) {
        return avaliacaoRepository.findById(id).map(avaliacao -> {
            avaliacaoRepository.delete(avaliacao);
            return true;
        }).orElse(false);
    }
}

