package com.cestec.cestec.model.cri;

import java.sql.Date;
import com.cestec.cestec.model.pcp_corretor;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

    @EmbeddedId
    private pcp_contratoId id;

    @ManyToOne
    @MapsId("codimovel")
    @JoinColumn(name = "codimovel")
    private pcp_imovel pcp_imovel;

    @ManyToOne
    @JoinColumn(name = "codcliente")
    private pcp_cliente pcp_cliente;

    @ManyToOne
    @JoinColumn(name = "codproprietario")
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
