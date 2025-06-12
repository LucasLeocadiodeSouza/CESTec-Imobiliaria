package com.cestec.cestec.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

    public pcp_meta(pcp_corretor pcp_corretor, double valor_meta, Date datinicio, Date datfinal, Integer situacao, Date datregistro, String ideusu) {
        this.pcp_corretor = pcp_corretor;
        this.valor_meta   = valor_meta;
        this.datinicio    = datinicio;
        this.datfinal     = datfinal;
        this.situacao     = situacao;
        this.datregistro  = datregistro;
        this.ideusu       = ideusu;
    }
}
