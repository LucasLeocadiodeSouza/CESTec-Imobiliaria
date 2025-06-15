package com.cestec.cestec.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pcp_imovel")
public class pcp_imovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codimovel;

    @ManyToOne
    @JoinColumn(name = "codproprietario", nullable = false)
    private pcp_proprietario pcp_proprietario;

    private String  endereco;
    private Integer quartos;
    private double  area;
    private double  vlrcondominio;
    private Integer status;
    private Date    datiregistro;
    private Date    datinicontrato;
    private double  preco;
    private Integer tipo;
    private Integer negociacao;


    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Integer negociacao) {
        this.negociacao = negociacao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDatiregistro() {
        return datiregistro;
    }

    public void setDatiregistro(Date datiregistro) {
        this.datiregistro = datiregistro;
    }

    public Date getDatinicontrato() {
        return datinicontrato;
    }

    public void setDatinicontrato(Date datinicontrato) {
        this.datinicontrato = datinicontrato;
    }

    public Integer getCodimovel() {
        return codimovel;
    }

    public void setCodimovel(Integer codimovel) {
        this.codimovel = codimovel;
    }

    public pcp_proprietario getPcp_proprietario() {
        return pcp_proprietario;
    }

    public void setPcp_proprietario(pcp_proprietario pcp_proprietario) {
        this.pcp_proprietario = pcp_proprietario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getQuartos() {
        return quartos;
    }

    public void setQuartos(Integer quartos) {
        this.quartos = quartos;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getVlrcondominio() {
        return vlrcondominio;
    }

    public void setVlrcondominio(double vlrcondominio) {
        this.vlrcondominio = vlrcondominio;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
