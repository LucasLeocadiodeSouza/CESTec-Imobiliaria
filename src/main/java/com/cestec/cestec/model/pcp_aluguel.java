package com.cestec.cestec.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcp_aluguel")
public class pcp_aluguel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codaluguel;

    @OneToOne
    @JoinColumn(name = "codimovel", nullable = false)
    private pcp_imovel pcp_imovel;

    @OneToOne
    @JoinColumn(name = "codcliente", nullable = false)
    private pcp_cliente pcp_cliente;

    //@OneToOne
    //List<pcp_corretor> pcp_corretor; 

    private Date    datinicio;
    private Date    datfinal;
    private Double  valor;
    private boolean ativo;

    
    public Integer getCodaluguel() {
        return codaluguel;
    }
    public void setCodaluguel(Integer codaluguel) {
        this.codaluguel = codaluguel;
    }    
    public pcp_imovel getPcp_imovel() {
        return pcp_imovel;
    }
    public void setPcp_imovel(pcp_imovel pcp_imovel) {
        this.pcp_imovel = pcp_imovel;
    }
    public pcp_cliente getPcp_cliente() {
        return pcp_cliente;
    }
    public void setPcp_cliente(pcp_cliente pcp_cliente) {
        this.pcp_cliente = pcp_cliente;
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
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    
}
