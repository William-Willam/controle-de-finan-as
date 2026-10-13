package com.williamsantos.controlefinancas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "meta")
public class Meta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "valor_alvo", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorAlvo;

    @Column(name = "valor_atual", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorAtual;

    @Column(nullable = false)
    private LocalDate prazo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    protected Meta() {
        // exigido pelo JPA
    }

    public Meta(String nome, BigDecimal valorAlvo, LocalDate prazo, Usuario usuario) {
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.valorAtual = BigDecimal.ZERO;
        this.prazo = prazo;
        this.usuario = usuario;
    }

    public boolean isConcluida() {
        return valorAtual.compareTo(valorAlvo) >= 0;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorAlvo() {
        return valorAlvo;
    }

    public void setValorAlvo(BigDecimal valorAlvo) {
        this.valorAlvo = valorAlvo;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}