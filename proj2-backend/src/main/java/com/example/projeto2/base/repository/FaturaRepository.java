package com.example.projeto2.base.repository;

import com.example.projeto2.base.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturaRepository extends JpaRepository<Fatura, Long> {
}
