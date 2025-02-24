package com.cestec.cestec.model;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class pcp_imovel_tipoID {

    private Integer codimovel;
    private Integer codtipo;

    public pcp_imovel_tipoID() {}

    public pcp_imovel_tipoID(Integer codimovel, Integer codtipo) {
        this.codimovel = codimovel;
        this.codtipo = codtipo;
    }

    public Integer getCodImovel() {
        return codimovel;
    }

    public void setCodImovel(Integer codimovel) {
        this.codimovel = codimovel;
    }

    public Integer getCodTipo() {
        return codtipo;
    }

    public void setCodTipo(Integer codtipo) {
        this.codtipo = codtipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        pcp_imovel_tipoID that = (pcp_imovel_tipoID) o;
        return Objects.equals(codimovel, that.codimovel) &&
               Objects.equals(codtipo, that.codtipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codimovel, codtipo);
    }
}
