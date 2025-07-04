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

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> procurarPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Avaliacao guardar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Optional<Avaliacao> atualizar(Long id, Avaliacao dados) {
        return avaliacaoRepository.findById(id).map(avaliacao -> {
            avaliacao.setNota(dados.getNota());
            avaliacao.setComentario(dados.getComentario());
            return avaliacaoRepository.save(avaliacao);
        });
    }

    public boolean deletar(Long id) {
        if (avaliacaoRepository.existsById(id)) {
            avaliacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
