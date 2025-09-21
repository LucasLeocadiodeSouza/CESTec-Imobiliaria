package com.cestec.cestec.infra.pagamento;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagadorInput {
    private Integer tipoInscricao;
    private String  numeroInscricao;
    private String  nome;
    private String  endereco;
    private Long    cep;
    private String  cidade;
    private String  bairro;
    private String  uf;
    private String  telefone;
}
