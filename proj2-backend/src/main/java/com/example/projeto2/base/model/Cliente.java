package com.example.projeto2.base.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
@SequenceGenerator(name = "ClienteIdGen", sequenceName = "idClienteSeq", allocationSize = 1)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ClienteIdGen")
    @Column(name = "idCliente", nullable = false)
    private Long idCliente;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nif", nullable = false, unique = true)
    private String nif;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "rua", nullable = true)
    private String rua;

    @Column(name = "nPorta", nullable = true)
    private Integer nPorta;

    @ManyToOne
    @JoinColumn(name = "idAvaliacao", nullable = true)
    private Avaliacao avaliacao;

    @ManyToOne
    @JoinColumn(name = "idCodPostal", nullable = true)
    private CodigoPostal codigoPostal;

    // Construtor padrão
    public Cliente() {}

    // Construtor completo
    public Cliente(String nome, String nif, String tel, String rua, Integer nPorta, Avaliacao avaliacao, CodigoPostal codigoPostal) {
        this.nome = nome;
        this.nif = nif;
        this.tel = tel;
        this.rua = rua;
        this.nPorta = nPorta;
        this.avaliacao = avaliacao;
        this.codigoPostal = codigoPostal;
    }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public Integer getNPorta() { return nPorta; }
    public void setNPorta(Integer nPorta) { this.nPorta = nPorta; }

    public Avaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Avaliacao avaliacao) { this.avaliacao = avaliacao; }

    public CodigoPostal getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(CodigoPostal codigoPostal) { this.codigoPostal = codigoPostal; }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                ", tel='" + tel + '\'' +
                ", rua='" + rua + '\'' +
                ", nPorta=" + nPorta +
                ", codigoPostal=" + (codigoPostal != null ? codigoPostal.getIdCodPostal() : "N/A") +
                ", avaliacao=" + (avaliacao != null ? avaliacao.getIdAvaliacao() : "N/A") +
                '}';
    }
}
