package com.cestec.cestec.model.contasAPagar;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "empresa")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//@MappedSuperclass
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long        id;

    private LocalDateTime   criadoEm;

    private LocalDateTime   atualizadoEm;

    @Column(length = 70)
    private String      razaoSocial;

    @Column(length = 20)
    private String      cnpj;

    @Column(length = 80)
    private String endereco_logradouro;

    @Column(length = 10)
    private String endereco_numero;

    @Column(length =  70)
    private String endereco_cidade;

    @Column(length = 70)
    private String endereco_bairro;

    @Column(length = 40)
    private String endereco_complemento;

    @Column(length = 2)
    private String endereco_uf;

    @Column(length = 20)
    private String endereco_cep;


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
    public String getRazaoSocial() {
        return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
