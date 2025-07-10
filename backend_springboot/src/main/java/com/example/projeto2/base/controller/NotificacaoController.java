package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Motorista;
import com.example.projeto2.base.model.Notificacao;
import com.example.projeto2.base.repository.MotoristaRepository;
import com.example.projeto2.base.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {
    @Autowired
    private NotificacaoService notificacaoService;
    @Autowired
    private MotoristaRepository motoristaRepository;

    @PostMapping("/motorista/{id}")
    public Notificacao criarNotificacao(@PathVariable Long id, @RequestBody String mensagem) {
        Optional<Motorista> motoristaOpt = motoristaRepository.findById(id);
        if (motoristaOpt.isPresent()) {
            return notificacaoService.criarNotificacao(motoristaOpt.get(), mensagem);
        }
        throw new RuntimeException("Motorista não encontrado");
    }

    @GetMapping("/motorista/{id}")
    public List<Notificacao> listarPorMotorista(@PathVariable Long id) {
        Optional<Motorista> motoristaOpt = motoristaRepository.findById(id);
        if (motoristaOpt.isPresent()) {
            return notificacaoService.listarPorMotorista(motoristaOpt.get());
        }
        throw new RuntimeException("Motorista não encontrado");
    }
} 