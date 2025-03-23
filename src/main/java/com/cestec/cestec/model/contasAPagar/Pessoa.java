package com.cestec.cestec.model.contasAPagar;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@MappedSuperclass
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long        id;

    @CreationTimestamp
    private LocalDate   criadoEm;

    @UpdateTimestamp
    private LocalDate   atualizadoEm;

    private String      nome;
    private String      documento;
    private boolean     pf;

    @Column(name = "endereco_logradouro")
    private String endereco_logradouro;

    @Column(name = "endereco_numero")
    private String endereco_numero;

    @Column(name = "endereco_cidade")
    private String endereco_cidade;

    @Column(name = "endereco_bairro")
    private String endereco_bairro;

    @Column(name = "endereco_complemento")
    private String endereco_complemento;

    @Column(name = "endereco_uf")
    private String endereco_uf;

    @Column(name = "endereco_cep")
    private String endereco_cep;


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
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public boolean isPf() {
        return pf;
    }
    public void setPf(boolean pf) {
        this.pf = pf;
    }
    public String getEndereco_logradouro() {
        return endereco_logradouro;
    }
    public void setEndereco_logradouro(String endereco_logradouro) {
        this.endereco_logradouro = endereco_logradouro;
    }
    public String getEndereco_numero() {
        return endereco_numero;
    }
    public void setEndereco_numero(String endereco_numero) {
        this.endereco_numero = endereco_numero;
    }
    public String getEndereco_cidade() {
        return endereco_cidade;
    }
    public void setEndereco_cidade(String endereco_cidade) {
        this.endereco_cidade = endereco_cidade;
    }
    public String getEndereco_bairro() {
        return endereco_bairro;
    }
    public void setEndereco_bairro(String endereco_bairro) {
        this.endereco_bairro = endereco_bairro;
    }
    public String getEndereco_complemento() {
        return endereco_complemento;
    }
    public void setEndereco_complemento(String endereco_complemento) {
        this.endereco_complemento = endereco_complemento;
    }
    public String getEndereco_uf() {
        return endereco_uf;
    }
    public void setEndereco_uf(String endereco_uf) {
        this.endereco_uf = endereco_uf;
    }
    public String getEndereco_cep() {
        return endereco_cep;
    }
    public void setEndereco_cep(String endereco_cep) {
        this.endereco_cep = endereco_cep;
    }

    
}
