package com.cestec.cestec.model;

public class ImovelProprietarioDTO {

    private Integer codimovel;
    private Integer codproprietario;
    private String nome;  // Nome do proprietário
    private Integer tipo;
    private Integer status;
    private Double preco;
    private Integer negociacao;

    public ImovelProprietarioDTO(){}
    
    public ImovelProprietarioDTO(Integer codimovel, Integer codproprietario, String nome, Integer tipo, Integer status, Double preco, Integer negociacao) {
        this.codimovel = codimovel;
        this.codproprietario = codproprietario;
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
        this.preco = preco;
        this.negociacao = negociacao;
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


}
