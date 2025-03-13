package com.cestec.cestec.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class contratoDTO {
    
    private Integer codcontrato;
    private Integer codimovel;
    private Integer codproprietario;
    private Integer codcliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date    datinicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date    datfinal;

    private Integer codtipo;
    private Integer codcorretor;
    private String  ideusuCorretor;
    private boolean ativo;
    private double  preco;
    private String  endereco;
    private double  tipo;
    private double  negociacao;
    private String  nomeProp;
    private String  nomeCliente;
    private double  valor;
    private String  ideusu;

    public contratoDTO(){}    

    public contratoDTO(Integer codcontrato, Integer codcliente, String nomeCliente, Integer codproprietario, String nomeProp, Integer codimovel, double tipo, double negociacao, double preco, Date datinicio, Date datfinal, double valor, String endereco) {
        this.codcontrato     = codcontrato;
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.codcliente      = codcliente;
        this.preco           = preco;
        this.tipo            = tipo;
        this.negociacao      = negociacao;
        this.nomeProp        = nomeProp;
        this.nomeCliente     = nomeCliente;
        this.datinicio       = datinicio;
        this.datfinal        = datfinal;
        this.valor           = valor;
        this.endereco        = endereco;
    } 
    
    public contratoDTO(Integer codcontrato, Integer codcliente, Integer codimovel, Integer codproprietario, Date datfinal, Date datinicio, double tipo, double negociacao, double preco) {
        this.codcontrato     = codcontrato;
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.codcliente      = codcliente;
        this.preco           = preco;
        this.tipo            = tipo;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getCodcorretor() {
        return codcorretor;
    }

    public void setCodcorretor(Integer codcorretor) {
        this.codcorretor = codcorretor;
    }

    public String getIdeusu() {
        return ideusu;
    }

    public void setIdeusu(String ideusu) {
        this.ideusu = ideusu;
    }

    public String getIdeusuCorretor() {
        return ideusuCorretor;
    }

    public void setIdeusuCorretor(String ideusuCorretor) {
        this.ideusuCorretor = ideusuCorretor;
    }
}