package com.example.projeto2.base.repository;

import com.example.projeto2.base.model.Saldo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaldoRepository extends JpaRepository<Saldo, Long> {
}