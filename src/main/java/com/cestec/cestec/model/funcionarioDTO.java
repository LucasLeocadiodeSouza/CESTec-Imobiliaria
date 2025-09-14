package com.cestec.cestec.model;

import java.math.BigDecimal;
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
    private Integer    codfunc;
    private String     nomefunc;
    private String     cpf;
    private String     numtel;
    private Integer    codsetor;
    private String     nomesetor;
    private Integer    codcargo;
    private String     nomecargo;
    private String     endereco;
    private BigDecimal salario;
    private LocalDate  datinasc;
    private LocalDate  criado_em;

    public funcionarioDTO(Integer codfunc, String nomefunc, String cpf, String numtel, Integer codsetor, String nomesetor, Integer codcargo, String nomecargo) {
        this.codfunc   = codfunc;
        this.nomefunc  = nomefunc;
        this.cpf       = cpf;
        this.numtel    = numtel;
        this.codsetor  = codsetor;
        this.nomesetor = nomesetor;
        this.codcargo  = codcargo;
        this.nomecargo = nomecargo;
    }
}
