package com.example.projeto2.base.model;

import jakarta.persistence.*;

@Entity
@Table(name = "codPostal")
@SequenceGenerator(name = "CodigoPostalIdGen", sequenceName = "idCodPostalSeq", allocationSize = 1)
public class CodigoPostal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CodigoPostalIdGen")
    @Column(name = "idCodPostal", nullable = false)
    private Long idCodPostal;

    @Column(name = "codLocalidade", nullable = false)
    private String codLocalidade;

    public CodigoPostal() {}

    public CodigoPostal(String codLocalidade) {
        this.codLocalidade = codLocalidade;
    }

    public Long getIdCodPostal() { return idCodPostal; }
    public void setIdCodPostal(Long idCodPostal) { this.idCodPostal = idCodPostal; }

    public String getCodLocalidade() { return codLocalidade; }
    public void setCodLocalidade(String codLocalidade) { this.codLocalidade = codLocalidade; }

    @Override
    public String toString() {
        return "CodigoPostal{" +
                "idCodPostal=" + idCodPostal +
                ", codLocalidade=" + codLocalidade +
                '}';
    }
}
