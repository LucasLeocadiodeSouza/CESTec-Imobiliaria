package com.cestec.cestec.model;

import java.sql.Date;

public class ImovelProprietarioDTO {

    private Integer codimovel;
    private Integer codproprietario;
    private String  nome;  // Nome do proprietário
    private Integer tipo;
    private Integer status;
    private Double  preco;
    private Integer negociacao;
    private String  endereco;
    private Double  area;
    private Integer quartos;
    private Double  vlrcondominio;
    private Date    datinicontrato;

    public ImovelProprietarioDTO(){}
    
    public ImovelProprietarioDTO(Integer codimovel, Integer codproprietario, String nome, Integer tipo, Integer status, Double preco, Integer negociacao) {
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.nome            = nome;
        this.tipo            = tipo;
        this.status          = status;
        this.preco           = preco;
        this.negociacao      = negociacao;
    }

    public ImovelProprietarioDTO(Integer codimovel, Integer codproprietario, String nome, Integer tipo, Integer status, Double preco, Integer negociacao, String endereco, Double area, Integer quartos, Double vlrcondominio, Date datinicontrato) {
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.nome            = nome;
        this.tipo            = tipo;
        this.status          = status;
        this.preco           = preco;
        this.negociacao      = negociacao;
        this.endereco        = endereco;
        this.area            = area;
        this.quartos         = quartos;
        this.vlrcondominio   = vlrcondominio;
        this.datinicontrato  = datinicontrato;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getQuartos() {
        return quartos;
    }

    public void setQuartos(Integer quartos) {
        this.quartos = quartos;
    }

    public Double getVlrcondominio() {
        return vlrcondominio;
    }

    public void setVlrcondominio(Double vlrcondominio) {
        this.vlrcondominio = vlrcondominio;
    }

    public Integer getCodimovel() {
        return codimovel;
    }

    public void setCodimovel(Integer codimovel) {
        this.codimovel = codimovel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Integer negociacao) {
        this.negociacao = negociacao;
    }

    public Integer getCodproprietario() {
        return codproprietario;
    }

    public void setCodproprietario(Integer codproprietario) {
        this.codproprietario = codproprietario;
    }

    public Date getDatinicontrato() {
        return datinicontrato;
    }

    public void setDatinicontrato(Date datinicontrato) {
        this.datinicontrato = datinicontrato;
    }


}
