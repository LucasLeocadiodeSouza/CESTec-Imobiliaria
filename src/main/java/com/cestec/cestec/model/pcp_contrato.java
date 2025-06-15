package com.cestec.cestec.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private float   valorliberado;
    private String  observacao;
    private String  ideusu;
    private boolean ativo;
    
    public pcp_contrato(pcp_cliente pcp_cliente,
                        pcp_imovel pcp_imovel,
                        pcp_proprietario pcp_proprietario,
                        Date datinicio, 
                        Date datfinal, 
                        Date datiregistro,
                        Integer situacao,
                        pcp_corretor pcp_corretor, 
                        float valor, 
                        boolean ativo) {

        this.pcp_cliente      = pcp_cliente;
        this.pcp_imovel       = pcp_imovel;
        this.pcp_proprietario = pcp_proprietario;
        this.datinicio        = datinicio;
        this.datfinal         = datfinal;
        this.datiregistro     = datiregistro;
        this.situacao         = situacao;
        this.pcp_corretor     = pcp_corretor;
        this.valor            = valor;
        this.ativo            = ativo;
    }
}
