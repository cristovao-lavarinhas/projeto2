package com.example.projeto2.base.service;

import com.example.projeto2.base.model.TipoPagamento;
import com.example.projeto2.base.repository.TipoPagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoPagamentoService {

    private final TipoPagamentoRepository tipoPagamentoRepository;

    public TipoPagamentoService(TipoPagamentoRepository tipoPagamentoRepository) {
        this.tipoPagamentoRepository = tipoPagamentoRepository;
    }

    // Listar todos os tipos de pagamento
    public List<TipoPagamento> listarTodos() {
        return tipoPagamentoRepository.findAll();
    }

    // Procurar tipo de pagamento por ID
    public Optional<TipoPagamento> procurarPorId(Long id) {
        return tipoPagamentoRepository.findById(id);
    }

    // Criar ou salvar um novo tipo de pagamento
    public TipoPagamento guardar(TipoPagamento tipoPagamento) {
        return tipoPagamentoRepository.save(tipoPagamento);
    }

    // Atualizar um tipo de pagamento existente
    public Optional<TipoPagamento> atualizar(Long id, TipoPagamento tipoPagamentoAtualizado) {
        return tipoPagamentoRepository.findById(id).map(tipoPagamento -> {
            tipoPagamento.setNome(tipoPagamentoAtualizado.getNome());
            return tipoPagamentoRepository.save(tipoPagamento);
        });
    }

    // Deletar um tipo de pagamento
    public boolean deletar(Long id) {
        return tipoPagamentoRepository.findById(id).map(tipoPagamento -> {
            tipoPagamentoRepository.delete(tipoPagamento);
            return true;
        }).orElse(false);
    }
}
