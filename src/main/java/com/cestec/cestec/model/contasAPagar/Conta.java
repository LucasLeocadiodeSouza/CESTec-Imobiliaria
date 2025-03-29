package com.cestec.cestec.model.contasAPagar;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "conta")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//@MappedSuperclass
public class Conta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private LocalDateTime   criadoEm;

    private LocalDateTime   atualizadoEm;

    @Column(length = 10)
    private String agencia;

    @Column(length = 15)
    private String conta;

    @Column(length = 1)
    private String digitoConta;

    @Column(length = 1)
    private String digitoAgencia;

    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    
    // @ManyToOne
    // @JoinColumn(name = "")
    // private Convenio    convenio;


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
    public String getAgencia() {
        return agencia;
    }
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }
    public String getConta() {
        return conta;
    }
    public void setConta(String conta) {
        this.conta = conta;
    }
    public String getDigitoConta() {
        return digitoConta;
    }
    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }
    public String getDigitoAgencia() {
        return digitoAgencia;
    }
    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }
    public Banco getBanco() {
        return banco;
    }
    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    // public Convenio getConvenio() {
    //     return convenio;
    // }
    // public void setConvenio(Convenio convenio) {
    //     this.convenio = convenio;
    // }
    
    
}
