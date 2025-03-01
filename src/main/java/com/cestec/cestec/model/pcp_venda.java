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
@Table(name = "pcp_venda")
public class pcp_venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codvenda;

    @OneToOne
    @JoinColumn(name = "codimovel", nullable = false)
    private pcp_imovel pcp_imovel;

    @OneToOne
    @JoinColumn(name = "codcliente", nullable = false)
    private pcp_cliente pcp_cliente;

    //@OneToOne
    //private List<pcp_corretor> pcp_corretor; 
    
    private Date    datvenda;
    private Double  valor;
    private boolean ativo;

    public Integer getCodvenda() {
        return codvenda;
    }
    public void setCodvenda(Integer codvenda) {
        this.codvenda = codvenda;
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
    public Date getDatvenda() {
        return datvenda;
    }
    public void setDatvenda(Date datvenda) {
        this.datvenda = datvenda;
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
