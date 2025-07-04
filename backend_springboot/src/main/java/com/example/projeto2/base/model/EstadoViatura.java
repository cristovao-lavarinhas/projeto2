package com.example.projeto2.base.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_viatura")
public class EstadoViatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "viatura_id")
    private Viatura viatura;

    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Viatura getViatura() { return viatura; }
    public void setViatura(Viatura viatura) { this.viatura = viatura; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}