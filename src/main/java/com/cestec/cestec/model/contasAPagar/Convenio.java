package com.cestec.cestec.model.contasAPagar;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "convenio")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//@MappedSuperclass
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long        id;

    private LocalDateTime   criadoEm;

    private LocalDateTime   atualizadoEm;

    @Column(length = 30)
    private String      numeroContrato;

    @Column(length = 5)
    private String      carteira;

    @Column(length = 5)
    private String      variacaoCarteira;
    
    private BigDecimal  jurosPorcentagem;
    private BigDecimal  multaPorcentagem;
    
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta       conta;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
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
    public String getVariacaoCarteira() {
        return variacaoCarteira;
    }
    public void setVariacaoCarteira(String variacaoCarteira) {
        this.variacaoCarteira = variacaoCarteira;
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
