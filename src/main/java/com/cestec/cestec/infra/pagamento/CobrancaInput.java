package com.cestec.cestec.infra.pagamento;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class CobrancaInput {

	private Long numeroConvenio;
	private Integer numeroCarteira;
	private Integer numeroVariacaoCarteira;
	private Integer codigoModalidade;
	private String dataEmissao; // dd.mm.aaaa
	private String dataVencimento; // dd.mm.aaaa
	private BigDecimal valorOriginal;
	private BigDecimal valorAbatimento;
	private Long quantidadeDiasProtesto;
	private Integer quantidadeDiasNegativacao;
	private Integer orgaoNegativador;
	private String indicadorAceiteTituloVencido;
	private Integer numeroDiasLimiteRecebimento; // informar se o valor anterior for S
	private char codigoAceite; // A ou N
	private Integer codigoTipoTitulo;
	private String descricaoTipoTitulo;
	private char indicadorPermissaoRecebimentoParcial; // S ou N
	private String numeroTituloBeneficiario;
	private String campoUtilizacaoBeneficiario;
	private String numeroTituloCliente;
	private String mensagemBloquetoOcorrencia;
	private String indicadorPix;
	private DescontoInput desconto;
	private JurosMoraInput jurosMora;
	private MultaInput multa;
	private PagadorInput pagador;
	
}
