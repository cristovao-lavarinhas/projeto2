package com.example.projeto2.base.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "UsuarioIdGen", sequenceName = "idUsuarioSeq", allocationSize = 1)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UsuarioIdGen")
    @Column(name = "idUsuario", nullable = false)
    private Long idUsuario;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo; // "ADMIN", "MOTORISTA", "CLIENTE"

    @Column(name = "nome", length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "idMotorista", nullable = true)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = true)
    private Cliente cliente;

    public Usuario() {}

    public Usuario(String email, String password, String tipo) {
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", email='" + email + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
} 