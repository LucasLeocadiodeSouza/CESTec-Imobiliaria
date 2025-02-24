package com.cestec.cestec.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcp_imovel_tipo")
public class pcp_imovel_tipo {

    @EmbeddedId
    private pcp_imovel_tipoID id;

    @ManyToOne
    @MapsId("codimovel") // Mapeia a FK para codImovel
    @JoinColumn(name = "codimovel")
    private pcp_imovel pcp_imovel;


    @Column(name = "datiregistro")
    private Date datiregistro;

    public pcp_imovel_tipo() {}

    public pcp_imovel_tipo(pcp_imovel_tipoID id, pcp_imovel pcp_imovel, Date datiregistro) {
        this.id = id;
        this.pcp_imovel = pcp_imovel;
        this.datiregistro = datiregistro;
    }

    public pcp_imovel_tipoID getId() {
        return id;
    }

    public void setId(pcp_imovel_tipoID id) {
        this.id = id;
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
