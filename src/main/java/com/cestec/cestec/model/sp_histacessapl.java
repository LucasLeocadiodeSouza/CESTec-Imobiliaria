package com.cestec.cestec.model;

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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "sp_histacessapl")
public class sp_histacessapl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="idmodulos", nullable = false)
    private sp_modulos idmodulos;

    @ManyToOne
    @JoinColumn(name="idaplicacao", nullable = false)
    private sp_aplicacoes idaplicacao;
    
    @ManyToOne
    @JoinColumn(name="idfunc", nullable = false)
    private funcionario idfunc;

    private Integer   numacess;
    private LocalDate atualizado_em;
    private LocalDate datregistro;
    private String    ideusu;
}
