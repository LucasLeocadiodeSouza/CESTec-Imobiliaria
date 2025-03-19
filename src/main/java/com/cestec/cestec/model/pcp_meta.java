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
@Table(name = "pcp_meta")
public class pcp_meta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codmeta;

    @OneToOne
    @JoinColumn(name="pcp_corretor_codcorretor", nullable = false)
    private pcp_corretor pcp_corretor;

    private double  valor_meta;
    private Date    datinicio;
    private Date    datfinal;
    private Integer situacao;
    private Date    datregistro;
    private String  ideusu;

    public pcp_meta(){}

    public pcp_meta(pcp_corretor pcp_corretor, double valor_meta, Date datinicio, Date datfinal, Integer situacao, Date datregistro, String ideusu) {
        this.pcp_corretor = pcp_corretor;
        this.valor_meta   = valor_meta;
        this.datinicio    = datinicio;
        this.datfinal     = datfinal;
        this.situacao     = situacao;
        this.datregistro  = datregistro;
        this.ideusu       = ideusu;
    }

    
    public Integer getCodmeta() {
        return codmeta;
    }
    public void setCodmeta(Integer codmeta) {
        this.codmeta = codmeta;
    }
    public pcp_corretor getPcp_corretor() {
        return pcp_corretor;
    }
    public void setPcp_corretor(pcp_corretor pcp_corretor) {
        this.pcp_corretor = pcp_corretor;
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
    public Date getDatregistro() {
        return datregistro;
    }
    public void setDatregistro(Date datregistro) {
        this.datregistro = datregistro;
    }
    public String getIdeusu() {
        return ideusu;
    }
    public void setIdeusu(String ideusu) {
        this.ideusu = ideusu;
    }
    public double getValor_meta() {
        return valor_meta;
    }
    public void setValor_meta(double valor_meta) {
        this.valor_meta = valor_meta;
    }
    public Integer getSituacao() {
        return situacao;
    }
    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }
}
