package com.cestec.cestec.model.contasAPagar;


import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@MappedSuperclass
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long          id;

    @Enumerated(EnumType.STRING)
    private TipoFatura    tipo;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tpPagamento;

    @Enumerated(EnumType.STRING)
    private SituacaoFat   situacao;

    private BigDecimal    valor;
    private LocalDate     dtVenc;
    private String        numDocumento;
    private String        nossoNumero;

    @CreationTimestamp
    private LocalDate     criadoEm;

    @UpdateTimestamp
    private LocalDate     atualizadoEm;

    @ManyToOne
    @JoinColumn(name="conta_id")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name="convenio_id")
    private Convenio convenio;

    @ManyToOne
    @JoinColumn(name="pessoa_id")
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public LocalDate getDtVenc() {
        return dtVenc;
    }
    public void setDtVenc(LocalDate dtVenc) {
        this.dtVenc = dtVenc;
    }
    public TipoFatura getTipo() {
        return tipo;
    }
    public void setTipo(TipoFatura tipo) {
        this.tipo = tipo;
    }
    public TipoPagamento getTpPagamento() {
        return tpPagamento;
    }
    public void setTpPagamento(TipoPagamento tpPagamento) {
        this.tpPagamento = tpPagamento;
    }
    public SituacaoFat getSituacao() {
        return situacao;
    }
    public void setSituacao(SituacaoFat situacao) {
        this.situacao = situacao;
    }
    public String getNumDocumento() {
        return numDocumento;
    }
    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }
    public String getNossoNumero() {
        return nossoNumero;
    }
    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
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
    public Conta getConta() {
        return conta;
    }
    public void setConta(Conta conta) {
        this.conta = conta;
    }
    public Convenio getConvenio() {
        return convenio;
    }
    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    
}
