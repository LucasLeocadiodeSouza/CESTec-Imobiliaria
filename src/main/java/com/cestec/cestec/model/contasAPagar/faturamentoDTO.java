package com.cestec.cestec.model.contasAPagar;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class faturamentoDTO {
    private Integer       codcliente;
    private String        nomecliente;
    private String        documento;
    private Integer       codcontrato;
    private Integer       codimovel;
    private Long          codfatura;
    private BigDecimal    valor;
    private LocalDate     vencimento;
    private TipoPagamento tipopag;
    private SituacaoFat   situacao;
    private String        numdoc;
    private Long          codconta;
}