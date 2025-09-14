package com.cestec.cestec.model.cri;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "pcp_imovel", fetch = FetchType.LAZY)
    private List<pcp_contrato> contratos;

    @ManyToOne
    @JoinColumn(name = "codproprietario", nullable = false)
    private pcp_proprietario pcp_proprietario;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "vlrcondominio", precision = 10, scale = 2)
    private BigDecimal vlrcondominio;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(length = 120)
    private String    endereco_bairro;

    @Column(length = 10)
    private String    endereco_numero;
    
    @Column(length = 20)
    private String    endereco_postal;

    @Column(length = 80)
    private String    endereco_cidade;

    @Column(length = 2)
    private String    endereco_estado;

    @Column(length = 120)
    private String    endereco_rua;

    private Integer   quartos;
    private Integer   banheiros;
    private Double    area;
    private Integer   status;
    private LocalDate datiregistro;
    private Date      datinicontrato;
    private Integer   tipo;
    private Integer   negociacao;
    private String    ideusu;
}
