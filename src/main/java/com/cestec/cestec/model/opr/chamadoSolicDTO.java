package com.cestec.cestec.model.opr;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class chamadoSolicDTO {
    private Integer   idsolic;
    private String    nomeusufila;
    private String    ideusufila;
    private String    titulo;
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
