package com.example.projeto2.base.repository;

import com.example.projeto2.base.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
