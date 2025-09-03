package com.cestec.cestec.model.cri;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pcp_imovel")
public class pcp_imovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codimovel;

    @ManyToOne
    @JoinColumn(name = "codproprietario", nullable = false)
    private pcp_proprietario pcp_proprietario;

    private String  endereco;
    private Integer quartos;
    private double  area;
    private double  vlrcondominio;
    private Integer status;
    private Date    datiregistro;
    private Date    datinicontrato;
    private double  preco;
    private Integer tipo;
    private Integer negociacao;
}
