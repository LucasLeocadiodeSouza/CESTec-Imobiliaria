package com.cestec.cestec.model.cri;

import java.sql.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class corretorDTO {
    
    private Integer codfuncionario;
    private Integer codcorretor;
    //private Integer codsetor;
    private Integer codmeta;
    private double  salario;
    private String  nome;
    private Date    datinasc;
    private String  documento;
    private String  numtel;
    private String  email;
    private String  login;
    private Integer idlogin;
    private double  vlrmeta;
    private Date    datiniciometa;
    private Date    datfinalmeta;
    private Integer situacao;

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

    public corretorDTO(Integer codcorretor, String email, Integer codfuncionario, String nome, String login) {
        this.codfuncionario = codfuncionario;
        this.codcorretor    = codcorretor;
        this.nome           = nome;
        this.email          = email;
        this.login          = login;
    }

    public corretorDTO(Integer codmeta,Integer codcorretor, String nome, double vlrmeta, Date datiniciometa, Date datfinalmeta, Integer situacao) {
        this.codmeta       = codmeta;
        this.codcorretor   = codcorretor;
        this.nome          = nome;
        this.vlrmeta       = vlrmeta;
        this.datiniciometa = datiniciometa;
        this.datfinalmeta  = datfinalmeta;
        this.situacao      = situacao;
    }

}
