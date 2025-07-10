package com.example.projeto2.base.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "motorista")
@SequenceGenerator(name = "MotoristaIdGen", sequenceName = "idMotoristaSeq", allocationSize = 1)
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "tel", nullable = false, length = 15)
    private String tel;

    @Column(name = "cartaConducao", nullable = false, length = 20)
    private String cartaConducao;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "morada", length = 200)
    private String morada;

    @Column(name = "iban", length = 34)
    private String iban;

    @Temporal(TemporalType.DATE)
    @Column(name = "dtNascimento", nullable = false)
    private Date dtNascimento;

    @ManyToOne
    @JoinColumn(name = "idAvaliacao")
    private Avaliacao avaliacao;

    public Motorista() {}

    public Motorista(String nome, String tel, String cartaConducao, Date dtNascimento, Avaliacao avaliacao) {
        this.nome = nome;
        this.tel = tel;
        this.cartaConducao = cartaConducao;
        this.dtNascimento = dtNascimento;
        this.avaliacao = avaliacao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getCartaConducao() { return cartaConducao; }
    public void setCartaConducao(String cartaConducao) { this.cartaConducao = cartaConducao; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public Date getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(Date dtNascimento) { this.dtNascimento = dtNascimento; }

    public Avaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Avaliacao avaliacao) { this.avaliacao = avaliacao; }

    @Override
    public String toString() {
        return "Motorista{" +
                "idMotorista=" + id +
                ", nome='" + nome + '\'' +
                ", tel='" + tel + '\'' +
                ", cartaConducao='" + cartaConducao + '\'' +
                ", dtNascimento=" + dtNascimento +
                '}';
    }
}