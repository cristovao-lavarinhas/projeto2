package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Notificacao;
import com.example.projeto2.base.model.Motorista;
import com.example.projeto2.base.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {
    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Notificacao criarNotificacao(Motorista motorista, String mensagem) {
        Notificacao notificacao = new Notificacao();
        notificacao.setMotorista(motorista);
        notificacao.setMensagem(mensagem);
        notificacao.setDataCriacao(LocalDateTime.now());
        notificacao.setLida(false);
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarPorMotorista(Motorista motorista) {
        return notificacaoRepository.findByMotorista(motorista);
    }
} 