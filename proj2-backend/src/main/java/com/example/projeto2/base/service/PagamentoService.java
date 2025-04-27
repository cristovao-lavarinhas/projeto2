package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Pagamento;
import com.example.projeto2.base.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    // Listar todos os pagamentos
    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    // Procurar pagamento por ID
    public Optional<Pagamento> procurarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    // Criar ou salvar um novo pagamento
    public Pagamento guardar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    // Atualizar pagamento existente
    public Optional<Pagamento> atualizar(Long id, Pagamento pagamentoAtualizado) {
        return pagamentoRepository.findById(id).map(pagamento -> {
            pagamento.setValor(pagamentoAtualizado.getValor());
            pagamento.setViagem(pagamentoAtualizado.getViagem());
            pagamento.setTipoPagamento(pagamentoAtualizado.getTipoPagamento());
            pagamento.setCliente(pagamentoAtualizado.getCliente());
            return pagamentoRepository.save(pagamento);
        });
    }

    // Deletar um pagamento
    public boolean deletar(Long id) {
        return pagamentoRepository.findById(id).map(pagamento -> {
            pagamentoRepository.delete(pagamento);
            return true;
        }).orElse(false);
    }
}

