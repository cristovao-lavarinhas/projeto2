package com.example.projeto2.base.repository;

import com.example.projeto2.base.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
