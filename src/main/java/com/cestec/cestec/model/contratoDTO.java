package com.cestec.cestec.model;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class contratoDTO {
    
    private Integer codcontrato;
    private Integer codimovel;
    private Integer codproprietario;
    private Integer codcliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date    datinicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date    datfinal;

    private Integer codcorretor;
    private String  ideusuCorretor;
    private boolean ativo;
    private double  preco;
    private double  tipo;
    private double  negociacao;
    private String  nomeProp;
    private String  nomeCliente;
    private String  nomeCorretor;
    private double  valor;
    private Integer situacao;
    private String  ideusu;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean pessoa_fisica;

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
        this.endereco_bairro = endereco;
    }
    
    public contratoDTO(Integer codcontrato, Integer codimovel, Integer codproprietario, Integer codcliente, Date datinicio, Date datfinal, Integer tipo, Integer codcorretor, double preco, double negociacao, String nomeProp, String nomeCliente, String nomeCorretor, double valor) {
        this.codcontrato     = codcontrato;
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.codcliente      = codcliente;
        this.datinicio       = datinicio;
        this.datfinal        = datfinal;
        this.tipo            = tipo;
        this.codcorretor     = codcorretor;
        this.preco           = preco;
        this.negociacao      = negociacao;
        this.nomeProp        = nomeProp;
        this.nomeCliente     = nomeCliente;
        this.nomeCorretor    = nomeCorretor;
        this.valor           = valor;
    }

    public contratoDTO(Integer codcontrato, Integer codcliente, Integer codimovel, Integer codproprietario, Date datfinal, Date datinicio, double tipo, double negociacao, double preco) {
        this.codcontrato     = codcontrato;
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.codcliente      = codcliente;
        this.preco           = preco;
        this.tipo            = tipo;
    }

}