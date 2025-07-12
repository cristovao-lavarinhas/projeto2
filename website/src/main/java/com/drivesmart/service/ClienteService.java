// src/main/java/com/drivesmart/service/ClienteService.java
package com.drivesmart.service;

import com.drivesmart.model.Cliente;
import com.drivesmart.repository.ClienteRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService implements UserDetailsService {

    private final ClienteRepository repo;
    private final PasswordEncoder   encoder;

    public ClienteService(ClienteRepository repo, PasswordEncoder encoder) {
        this.repo   = repo;
        this.encoder = encoder;
    }

    /* -------- registo -------- */
    @Transactional
    public void registar(String email, String rawPassword) {
        if (repo.existsByEmail(email))
            throw new IllegalArgumentException("E-mail já registado");

        Cliente c = new Cliente();
        c.setEmail(email);
        c.setPassword(encoder.encode(rawPassword));
        repo.save(c);
    }

    /* -------- login -------- */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente c = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado"));

        return User.withUsername(c.getEmail())
                .password(c.getPassword())
                .roles("CLIENTE")        // ROLE_CLIENTE
                .build();
    }

    /* -------- listagem p/ controller -------- */
    public List<Cliente> listarTodos() { return repo.findAll(); }
}
