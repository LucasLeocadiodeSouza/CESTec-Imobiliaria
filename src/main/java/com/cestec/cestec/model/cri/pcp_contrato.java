package com.cestec.cestec.model.cri;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import jakarta.persistence.Column;
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
    private Integer id;

    @ManyToOne
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

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "valorliberado", precision = 10, scale = 2)
    private BigDecimal valorliberado;

    private Date      datinicio;
    private Date      datfinal;
    private Integer   situacao;
    private LocalDate datiregistro;
    private String    observacao;
    private String    ideusu;
    private boolean   ativo;
    
    public pcp_contrato(pcp_cliente pcp_cliente,
                        pcp_imovel pcp_imovel,
                        pcp_proprietario pcp_proprietario,
                        Date datinicio, 
                        Date datfinal, 
                        LocalDate datiregistro,
                        Integer situacao,
                        pcp_corretor pcp_corretor, 
                        BigDecimal valor, 
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
