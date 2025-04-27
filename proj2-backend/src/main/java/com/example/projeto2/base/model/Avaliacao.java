package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "avaliacao")
@SequenceGenerator(name = "AvaliacaoIdGen", sequenceName = "idAvaliacaoSeq", allocationSize = 1)
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AvaliacaoIdGen")
    @Column(name = "idAvaliacao", nullable = false)
    private Long idAvaliacao;

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "comentario")
    private String comentario;

    @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL)
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL)
    private List<Motorista> motoristas;

    public Avaliacao() {}

    public Avaliacao(Integer nota, String comentario, List<Cliente> clientes, List<Motorista> motoristas) {
        this.nota = nota;
        this.comentario = comentario;
        this.clientes = clientes;
        this.motoristas = motoristas;
    }

    public Long getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Long idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }

    public List<Motorista> getMotoristas() { return motoristas; }
    public void setMotoristas(List<Motorista> motoristas) { this.motoristas = motoristas; }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "idAvaliacao=" + idAvaliacao +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
