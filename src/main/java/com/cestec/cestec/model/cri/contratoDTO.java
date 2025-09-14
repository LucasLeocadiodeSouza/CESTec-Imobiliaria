package com.cestec.cestec.model.cri;

import java.math.BigDecimal;
import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class contratoDTO {
    
    private Integer codcontrato;
    private Integer codimovel;
    private Integer codproprietario;
    private Integer codcliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date    datinicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date    datfinal;

    private Integer    codcorretor;
    private String     ideusuCorretor;
    private Boolean    ativo;
    private BigDecimal preco;
    private Integer    tipo;
    private Integer    negociacao;
    private String     nomeProp;
    private String     nomeCliente;
    private String     nomeCorretor;
    private BigDecimal valor;
    private String     documento;
    private String     endereco_postal;
    private Integer    quartos;
    private Integer    banheiros;
    private Double     area;
    private BigDecimal vlrcondominio;
    private BigDecimal valorliberado;
    private String     observacao;
    private Integer    situacao;
    private String     ideusu;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean pessoa_fisica;

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

    public contratoDTO(Integer     codcontrato, 
                       Integer     codcliente, 
                       String      nomeCliente, 
                       Integer     codproprietario, 
                       String      nomeProp, 
                       Integer     codimovel, 
                       Integer     tipo, 
                       Integer     negociacao, 
                       BigDecimal  preco, 
                       Date        datinicio, 
                       Date        datfinal, 
                       BigDecimal  valor, 
                       String      endereco_bairro,
                       String      endereco_numero,
                       String      endereco_postal,
                       String      endereco_cidade,
                       String      endereco_uf,
                       String      endereco_logradouro,
                       Integer     codcorretor, 
                       Integer     situacao) {

        this.codcontrato         = codcontrato;
        this.codimovel           = codimovel;
        this.codproprietario     = codproprietario;
        this.codcliente          = codcliente;
        this.preco               = preco;
        this.tipo                = tipo;
        this.negociacao          = negociacao;
        this.nomeProp            = nomeProp;
        this.nomeCliente         = nomeCliente;
        this.datinicio           = datinicio;
        this.datfinal            = datfinal;
        this.valor               = valor;
        this.endereco_bairro     = endereco_bairro;
        this.endereco_numero     = endereco_numero;
        this.endereco_postal     = endereco_postal;
        this.endereco_cidade     = endereco_cidade;
        this.endereco_uf         = endereco_uf;
        this.endereco_logradouro = endereco_logradouro;
        this.codcorretor         = codcorretor;
        this.situacao            = situacao;
    }
    
    public contratoDTO(Integer    codcontrato, 
                       Integer    codimovel, 
                       Integer    codproprietario, 
                       Integer    codcliente, 
                       Date       datinicio, 
                       Date       datfinal, 
                       Integer    tipo, 
                       Integer    codcorretor, 
                       BigDecimal preco, 
                       Integer    negociacao, 
                       String     nomeProp, 
                       String     nomeCliente, 
                       String     nomeCorretor, 
                       BigDecimal valor,
                       Integer    quartos,
                       Integer    banheiros,
                       BigDecimal vlrcondominio, 
                       Double     area, 
                       String     documento, 
                       String     endereco_bairro,
                       String     endereco_numero,
                       String     endereco_postal,
                       String     endereco_cidade,
                       String     endereco_uf,
                       String     endereco_logradouro,
                       BigDecimal valorliberado, 
                       String     observacao, 
                       Boolean    pessoa_fisica) {

        this.codcontrato         = codcontrato;
        this.codimovel           = codimovel;
        this.codproprietario     = codproprietario;
        this.codcliente          = codcliente;
        this.datinicio           = datinicio;
        this.datfinal            = datfinal;
        this.tipo                = tipo;
        this.codcorretor         = codcorretor;
        this.preco               = preco;
        this.negociacao          = negociacao;
        this.nomeProp            = nomeProp;
        this.nomeCliente         = nomeCliente;
        this.nomeCorretor        = nomeCorretor;
        this.valor               = valor;
        this.quartos             = quartos;
        this.banheiros           = banheiros;
        this.vlrcondominio       = vlrcondominio;
        this.area                = area;
        this.documento           = documento;
        this.endereco_bairro     = endereco_bairro;
        this.endereco_numero     = endereco_numero;
        this.endereco_postal     = endereco_postal;
        this.endereco_cidade     = endereco_cidade;
        this.endereco_uf         = endereco_uf;
        this.endereco_logradouro = endereco_logradouro;
        this.valorliberado       = valorliberado;
        this.observacao          = observacao;
        this.pessoa_fisica       = pessoa_fisica;
    }

    public contratoDTO(Integer codcontrato, Integer codcliente, Integer codimovel, Integer codproprietario, Date datfinal, Date datinicio, Integer tipo, Integer negociacao, BigDecimal  preco) {
        this.codcontrato     = codcontrato;
        this.codimovel       = codimovel;
        this.codproprietario = codproprietario;
        this.codcliente      = codcliente;
        this.preco           = preco;
        this.tipo            = tipo;
    }

}