package com.cestec.cestec.model;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sp_libacesso")
public class LibAcesso {
    
    @EmbeddedId
    private LibAcessoId id;
    
    @Column(name = "datregistro")
    @Temporal(TemporalType.DATE)
    private Date dataRegistro;
    
    @Column(name = "ideusu")
    private String ideusu;

    @Column(name = "datvenc")
    @Temporal(TemporalType.DATE)
    private Date datvenc;

}