package com.cestec.cestec.model;

import java.sql.Date;

public class contratoDTO {
    
    private Integer codcontrato;
    private Integer codimovel;
    private Integer codproprietario;
    private Integer codcliente;
    private Date    datinicio;
    private Date    datfinal;
    private Integer codtipo;
    private Integer codcorretor;
    private boolean ativo;
    private double  preco;
    private double  endereco;
    private double  tipo;
    private double  negociacao;
    private String  nomeProp;
    private String  nomeCliente;

    public contratoDTO(){}    

    public contratoDTO(Integer codcontrato, Integer codcliente, String nomeCliente, Integer codproprietario, String nomeProp, Integer codimovel, double tipo, double negociacao, double preco) {
        this.codcontrato     = codcontrato;
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.codcliente      = codcliente;
        this.preco           = preco;
        this.tipo            = tipo;
        this.negociacao      = negociacao;
        this.nomeProp        = nomeProp;
        this.nomeCliente     = nomeCliente;
    }

    public Integer getCodcontrato() {
        return codcontrato;
    }

    public void setCodcontrato(Integer codcontrato) {
        this.codcontrato = codcontrato;
    }

    public Integer getCodtipo() {
        return codtipo;
    }

    public void setCodtipo(Integer codtipo) {
        this.codtipo = codtipo;
    }

    public Integer getCodcorretor() {
        return codcorretor;
    }

    public void setCodcorretor(Integer codcorretor) {
        this.codcorretor = codcorretor;
    }

    public Integer getCodproprietario() {
        return codproprietario;
    }

    public void setCodproprietario(Integer codproprietario) {
        this.codproprietario = codproprietario;
    }

    public Integer getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(Integer codcliente) {
        this.codcliente = codcliente;
    }

    public String getNomeProp() {
        return nomeProp;
    }

    public void setNomeProp(String nomeProp) {
        this.nomeProp = nomeProp;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getEndereco() {
        return endereco;
    }

    public void setEndereco(double endereco) {
        this.endereco = endereco;
    }

    public double getTipo() {
        return tipo;
    }

    public void setTipo(double tipo) {
        this.tipo = tipo;
    }

    public double getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(double negociacao) {
        this.negociacao = negociacao;
    }

    public Integer getCodimovel() {
        return codimovel;
    }

    public void setCodimovel(Integer codimovel) {
        this.codimovel = codimovel;
    }

    public Date getDatinicio() {
        return datinicio;
    }

    public void setDatinicio(Date datinicio) {
        this.datinicio = datinicio;
    }

    public Date getDatfinal() {
        return datfinal;
    }

    public void setDatfinal(Date datfinal) {
        this.datfinal = datfinal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
