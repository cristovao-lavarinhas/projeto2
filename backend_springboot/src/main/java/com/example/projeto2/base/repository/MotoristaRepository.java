package com.example.projeto2.base.repository;

import com.example.projeto2.base.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
}
