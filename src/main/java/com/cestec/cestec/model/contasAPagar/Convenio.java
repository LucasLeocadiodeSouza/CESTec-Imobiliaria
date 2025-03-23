package com.cestec.cestec.model.contasAPagar;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@MappedSuperclass
public class Convenio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long        id;

    @CreationTimestamp
    private LocalDate   criadoEm;

    @UpdateTimestamp
    private LocalDate   atualizadoEm;

    private String      numeroContrato;
    private String      carteira;
    private String      variacaoCateira;
    private BigDecimal  jurosPorcentagem;
    private BigDecimal  multaPorcentagem;
    private Conta       conta;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getCriadoEm() {
        return criadoEm;
    }
    public void setCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }
    public LocalDate getAtualizadoEm() {
        return atualizadoEm;
    }
    public void setAtualizadoEm(LocalDate atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
    public String getNumeroContrato() {
        return numeroContrato;
    }
    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }
    public String getCarteira() {
        return carteira;
    }
    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }
    public String getVariacaoCateira() {
        return variacaoCateira;
    }
    public void setVariacaoCateira(String variacaoCateira) {
        this.variacaoCateira = variacaoCateira;
    }
    public BigDecimal getJurosPorcentagem() {
        return jurosPorcentagem;
    }
    public void setJurosPorcentagem(BigDecimal jurosPorcentagem) {
        this.jurosPorcentagem = jurosPorcentagem;
    }
    public BigDecimal getMultaPorcentagem() {
        return multaPorcentagem;
    }
    public void setMultaPorcentagem(BigDecimal multaPorcentagem) {
        this.multaPorcentagem = multaPorcentagem;
    }
    public Conta getConta() {
        return conta;
    }
    public void setConta(Conta conta) {
        this.conta = conta;
    }

    
}
