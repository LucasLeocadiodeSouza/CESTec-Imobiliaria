package com.cestec.cestec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcp_imovel")
public class pcp_imovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codimovel;

    @ManyToOne
    @JoinColumn(name = "codproprietario", nullable = false)
    private pcp_proprietario pcp_proprietario;

    private String endereco;
    private int    qtdQuartos;
    private double area;
    private double vlrcondominio;
    private double preco;    


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

    public int getQtdQuartos() {
        return qtdQuartos;
    }

    public void setQtdQuartos(int qtdQuartos) {
        this.qtdQuartos = qtdQuartos;
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

    /*
	tipo int,
	status int,
	negociacao int
    */

}
