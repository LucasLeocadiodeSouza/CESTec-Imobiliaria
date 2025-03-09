package com.cestec.cestec.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcp_contrato_tipo")
public class pcp_contrato_tipo {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codtipo;

    @OneToOne
    @JoinColumn(name = "codcontrato", nullable = false)
    private pcp_contrato pcp_contrato;

    @ManyToOne
    @JoinColumn(name = "codimovel", nullable = false)
    private pcp_imovel pcp_imovel;

    private Date datiregistro;


    public Integer getCodcontrato() {
        return codtipo;
    }

    public void setCodcontrato(Integer codtipo) {
        this.codtipo = codtipo;
    }

    public pcp_contrato getPcp_contrato() {
        return pcp_contrato;
    }

    public void setPcp_contrato(pcp_contrato pcp_contrato) {
        this.pcp_contrato = pcp_contrato;
    }

    public pcp_imovel getPcp_imovel() {
        return pcp_imovel;
    }

    public void setPcp_imovel(pcp_imovel pcp_imovel) {
        this.pcp_imovel = pcp_imovel;
    }

    public Date getDatiregistro() {
        return datiregistro;
    }

    public void setDatiregistro(Date datiregistro) {
        this.datiregistro = datiregistro;
    }
}
