// src/main/java/com/drivesmart/repository/ClienteRepository.java
package com.drivesmart.repository;

import com.drivesmart.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
    Optional<Cliente> findByEmail(String email);
}
