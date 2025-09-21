package com.cestec.cestec.model.contasAPagar;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "convenio")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//@MappedSuperclass
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long        id;

    private LocalDateTime   criadoEm;

    private LocalDateTime   atualizadoEm;

    @Column(length = 30)
    private String      numeroContrato;

    @Column(length = 5)
    private String      carteira;

    @Column(length = 5)
    private String      variacaoCarteira;
    
    private BigDecimal  jurosPorcentagem;
    private BigDecimal  multaPorcentagem;
    
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta       conta;    
}
