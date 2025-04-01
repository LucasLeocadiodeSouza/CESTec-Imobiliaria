package com.cestec.cestec.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
