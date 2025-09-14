package com.cestec.cestec.model;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImovelProprietarioDTO {
    private Integer    codimovel;
    private Integer    codproprietario;
    private String     nome;  // Nome do proprietário
    private Integer    tipo;
    private Integer    status;
    private BigDecimal preco;
    private Integer    negociacao;
    private String     endereco_bairro;
    private String     endereco_numero;
    private String     endereco_postal;
    private String     endereco_cidade;
    private String     endereco_estado;
    private String     endereco_rua;
    private Double     area;
    private Integer    quartos;
    private Integer    banheiros;
    private BigDecimal vlrcondominio;
    private Date       datinicontrato;

    public ImovelProprietarioDTO(){}
    
    public ImovelProprietarioDTO(Integer codimovel, Integer codproprietario, String nome, Integer tipo, Integer status, BigDecimal preco, Integer negociacao) {
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.nome            = nome;
        this.tipo            = tipo;
        this.status          = status;
        this.preco           = preco;
        this.negociacao      = negociacao;
    }

    public ImovelProprietarioDTO(Integer    codimovel, 
                                 Integer    codproprietario, 
                                 String     nome, 
                                 Integer    tipo, 
                                 Integer    status, 
                                 BigDecimal preco, 
                                 Integer    negociacao, 
                                 String     endereco_bairro, 
                                 String     endereco_numero, 
                                 String     endereco_postal,
                                 String     endereco_cidade,
                                 String     endereco_estado,
                                 String     endereco_rua,
                                 Double     area, 
                                 Integer    quartos, 
                                 BigDecimal vlrcondominio, 
                                 Date       datinicontrato, 
                                 Integer    banheiros) {

        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.nome            = nome;
        this.tipo            = tipo;
        this.status          = status;
        this.preco           = preco;
        this.negociacao      = negociacao;
        this.area            = area;
        this.quartos         = quartos;
        this.vlrcondominio   = vlrcondominio;
        this.datinicontrato  = datinicontrato;
        this.banheiros       = banheiros;
        this.endereco_bairro = endereco_bairro;
        this.endereco_numero = endereco_numero;
        this.endereco_postal = endereco_postal;
        this.endereco_cidade = endereco_cidade;
        this.endereco_estado = endereco_estado;
        this.endereco_rua    = endereco_rua;
    }

}
