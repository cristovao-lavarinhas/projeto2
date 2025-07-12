// src/main/java/com/drivesmart/model/Cliente.java
package com.drivesmart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cliente")           // já existe na tua BD
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email @NotBlank
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;       // guarda hash bcrypt

    /* ==== getters / setters ==== */
    public Long getId()               { return id; }
    public String getEmail()          { return email; }
    public String getPassword()       { return password; }

    public void setEmail(String email){ this.email = email; }
    public void setPassword(String pw){ this.password = pw; }
}
