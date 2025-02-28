package com.cestec.cestec.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcp_proprietario")
public class pcp_proprietario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codproprietario;

    @OneToMany(mappedBy = "pcp_proprietario", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<pcp_imovel> imoveis;

    private String cpf;
    private String cnpj;
    private String numtel;
    private String email;
    private String endereco; 
    private String nome;

    public List<pcp_imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<pcp_imovel> imoveis) {
        this.imoveis = imoveis;
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

    public Integer getCodproprietario(){
        return codproprietario;
    }

    public void setCodproprietario(Integer codproprietario){
        this.codproprietario = codproprietario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
