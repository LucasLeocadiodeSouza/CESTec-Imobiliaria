package com.cestec.cestec.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class funcionarioDTO {
    private Integer codfunc;
    private String  nomefunc;
    private String  cpf;
    private String  numtel;
    private Integer codsetor;
    private String  nomesetor;
    private Integer codcargo;
    private String  nomecargo;


}
