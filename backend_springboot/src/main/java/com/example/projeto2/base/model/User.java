package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "idUser", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID idUser;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters e setters
    public UUID getIdUser() { return idUser; }
    public void setIdUser(UUID idUser) { this.idUser = idUser; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 