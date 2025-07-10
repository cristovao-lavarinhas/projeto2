package com.example.projeto2.base.repository;

import com.example.projeto2.base.model.Notificacao;
import com.example.projeto2.base.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByMotorista(Motorista motorista);
} 