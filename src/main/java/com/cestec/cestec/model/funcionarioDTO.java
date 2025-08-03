package com.cestec.cestec.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class funcionarioDTO {
    private Integer   codfunc;
    private String    nomefunc;
    private String    cpf;
    private String    numtel;
    private Integer   codsetor;
    private String    nomesetor;
    private Integer   codcargo;
    private String    nomecargo;
    private String    endereco;
    private double    salario;
    private LocalDate datinasc;
    private LocalDate criado_em;

    //func.codfuncionario, func.nome, func.cpf, func.numtel, set.codsetor, set.nome, car.id, car.nome, func.endereco, func.salario, func.datinasc, func.criado_em, func.datinasc
}
