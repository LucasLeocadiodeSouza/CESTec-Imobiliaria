package com.cestec.cestec.model.opr;

import java.time.LocalDate;
import com.cestec.cestec.model.funcionario;
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
@Entity(name = "opr_chamados_solic")
public class opr_chamados_solic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "codfuncfila", nullable = false)
    private funcionario codfuncfila;

    @Column(length = 50)
    private String  titulo;

    private String    descricao;
    private Integer   estado;
    private Integer   prioridade;
    private Integer   complex;
    private LocalDate previsao;
    private String    obs;
    private LocalDate datconcl;
    private String    feedback;
    private LocalDate datregistro;
    private String    ideususolic;
}
