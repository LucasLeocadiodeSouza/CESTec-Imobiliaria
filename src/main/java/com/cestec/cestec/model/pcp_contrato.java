package com.cestec.cestec.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcp_contrato")
public class pcp_contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codcontrato;

    @ManyToOne
    @JoinColumn(name = "codcliente", nullable = false)
    private pcp_cliente pcp_cliente;

    @ManyToOne
    @JoinColumn(name = "codimovel", nullable = false)
    private pcp_imovel pcp_imovel;

    @ManyToOne
    @JoinColumn(name = "codproprietario", nullable = false)
    private pcp_proprietario pcp_proprietario;

    @ManyToOne
    @JoinColumn(name = "codcorretor")
    private pcp_corretor pcp_corretor;

    private Date    datinicio;
    private Date    datfinal;
    private Integer situacao;
    private Date    datiregistro;
    private float   valor;
    private boolean ativo;

    public pcp_contrato(){}
    
    public pcp_contrato(pcp_cliente pcp_cliente,
                        pcp_imovel pcp_imovel,
                        pcp_proprietario pcp_proprietario,
                        Date datinicio, 
                        Date datfinal, 
                        Date datiregistro,
                        pcp_corretor pcp_corretor, 
                        float valor, 
                        boolean ativo) {

        this.pcp_cliente      = pcp_cliente;
        this.pcp_imovel       = pcp_imovel;
        this.pcp_proprietario = pcp_proprietario;
        this.datinicio        = datinicio;
        this.datfinal         = datfinal;
        this.datiregistro     = datiregistro;
        this.pcp_corretor     = pcp_corretor;
        this.valor            = valor;
        this.ativo            = ativo;
    }

    public Integer getCodcontrato() {
        return codcontrato;
    }
    public void setCodcontrato(Integer codcontrato) {
        this.codcontrato = codcontrato;
    }
    public pcp_cliente getPcp_cliente() {
        return pcp_cliente;
    }
    public void setPcp_cliente(pcp_cliente pcp_cliente) {
        this.pcp_cliente = pcp_cliente;
    }
    public pcp_imovel getPcp_imovel() {
        return pcp_imovel;
    }
    public void setPcp_imovel(pcp_imovel pcp_imovel) {
        this.pcp_imovel = pcp_imovel;
    }
    public pcp_proprietario getPcp_proprietario() {
        return pcp_proprietario;
    }
    public void setPcp_proprietario(pcp_proprietario pcp_proprietario) {
        this.pcp_proprietario = pcp_proprietario;
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
    public Date getDatiregistro() {
        return datiregistro;
    }
    public void setDatiregistro(Date datiregistro) {
        this.datiregistro = datiregistro;
    }
    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    public pcp_corretor getPcp_corretor() {
        return pcp_corretor;
    }

    public void setPcp_corretor(pcp_corretor pcp_corretor) {
        this.pcp_corretor = pcp_corretor;
    }

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }
}
