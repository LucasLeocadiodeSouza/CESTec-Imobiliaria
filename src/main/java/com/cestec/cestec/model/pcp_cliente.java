package com.cestec.cestec.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcp_cliente")
public class pcp_cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codcliente;

    @OneToOne(mappedBy = "pcp_cliente", cascade = CascadeType.ALL, orphanRemoval = false)    
    @JsonIgnore
    private pcp_contrato pcp_contrato;

    private String nome;
    private String cpf;
    private String cnpj;
    private String numtel;
    private String email;
    private String endereco;
    
    public pcp_contrato getPcp_contrato() {
        return pcp_contrato;
    }
    public void setPcp_contrato(pcp_contrato pcp_contrato) {
        this.pcp_contrato = pcp_contrato;
    }
    public Integer getCodcliente() {
        return codcliente;
    }
    public void setCodcliente(Integer codcliente) {
        this.codcliente = codcliente;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getNumtel() {
        return numtel;
    }
    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
