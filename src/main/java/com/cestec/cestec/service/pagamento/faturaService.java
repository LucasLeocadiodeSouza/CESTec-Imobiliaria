package com.cestec.cestec.service.pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cestec.cestec.controller.pagamento.CobrancaController;
import com.cestec.cestec.infra.pagamento.CobrancaInput;
import com.cestec.cestec.infra.pagamento.DescontoInput;
import com.cestec.cestec.infra.pagamento.JurosMoraInput;
import com.cestec.cestec.infra.pagamento.MultaInput;
import com.cestec.cestec.infra.pagamento.PagadorInput;
import com.cestec.cestec.infra.pagamento.controller.AcessTokenController;
import com.cestec.cestec.model.pcp_cliente;
import com.cestec.cestec.model.contasAPagar.BoletoRegistrado;
import com.cestec.cestec.model.contasAPagar.Cobranca;
import com.cestec.cestec.model.contasAPagar.Fatura;
import com.cestec.cestec.model.contasAPagar.FaturaRegistrada;
import com.cestec.cestec.repository.clienteRepository;
import com.cestec.cestec.repository.pagamento.contaRepository;
import com.cestec.cestec.repository.pagamento.convenioRepository;
import com.cestec.cestec.repository.pagamento.faturaRegistradaRepository;
import com.cestec.cestec.repository.pagamento.faturaRepository;
import com.cestec.cestec.util.Normalizador;

import jakarta.transaction.Transactional;

@Service
public class faturaService {
    
    @Autowired
	private faturaRepository repository;

    @Autowired
	private contaRepository contarepository;

	@Autowired
	private convenioRepository conveniorepository;

	@Autowired
	private clienteRepository clienterepository;

    @Autowired
	private AcessTokenController acessTokenController;

    @Autowired
    private CobrancaController cobrancacontroller;

    @Autowired
    private faturaRegistradaRepository faturaRegRepos;
	
    @Autowired
    private geradorBoleto geradorboleto;

    public byte[] gerar(Long faturaId){

        var fatura = repository.getOne(faturaId);
        var cobranca = transformarFaturaEmCobranca(faturaId);

        return geradorboleto.gerar(fatura, cobranca);
    }

  
    public BoletoRegistrado registrarBoleto(Long faturaId, Cobranca cobranca){        
        var token  = acessTokenController.requisitarToken(cobranca.getClienteId(),cobranca.getClienteSecret());
        
        var fatura = repository.getOne(faturaId);


        var boletoRegistrado = cobrancacontroller.register(transformarFaturaEmCobranca(faturaId),token,cobranca.getAppKey());        

        System.out.println(Long.valueOf(boletoRegistrado.getNumero()).toString());

        fatura.setNossoNumero(Long.valueOf(boletoRegistrado.getNumero()).toString());

        var faturaReg = new FaturaRegistrada().criar(fatura, boletoRegistrado.getLinhaDigitavel(), boletoRegistrado.getQrCode().getUrl(),boletoRegistrado.getQrCode().getEmv());

        faturaRegRepos.save(faturaReg);

        return boletoRegistrado;
    }
    
	public CobrancaInput transformarFaturaEmCobranca(Long faturaId) {
		var fatura = repository.getOne(faturaId);
		
		return criarFatura(fatura);
	}

    public Fatura salvarFaturaNoBanco(Integer clienteId, Fatura fatura){
        var conta    = contarepository.findById( 1);
		var convenio = conveniorepository.findById(1);
		var pessoa   = clienterepository.findByCodcliente(clienteId);

		fatura.setNumeroDocumento("71900");
		fatura.setConta(conta);
		fatura.setConvenio(convenio);
		fatura.setPessoa(pessoa);
        
		return repository.save(fatura);
    }

    public CobrancaInput criarFatura(Fatura fatura){
        
        var desconto = DescontoInput.builder()
                                    .tipo(0)
                                    .porcentagem(null)
                                    .valor(null)
                                    .dataExpiracao(null)
                                    .build();

        var jurosMora = JurosMoraInput.builder()
                                      .tipo(2)
                                      .porcentagem(fatura.getConvenio().getJurosPorcentagem())
                                      .valor(BigDecimal.ZERO)
                                      .build();

        var multa = MultaInput.builder()
                              .tipo(2)
                              .data(converterData(fatura.getData_vencimento().plusDays(2)))
                              .porcentagem(fatura.getConvenio().getJurosPorcentagem())
                              .valor(BigDecimal.ZERO)
                              .build();

        var pessoa = fatura.getPessoa();

        var pagamento = PagadorInput.builder()
                                    .tipoInscricao(pessoa.isPf()?1:2)
                                    .numeroInscricao(pessoa.getDocumento())
                                    .nome(Normalizador.norm(pessoa.getNome()))
                                    .cep(Long.valueOf(pessoa.getEndereco_cep().replaceAll("[^\\d]", "")))
                                    .cidade(Normalizador.norm(pessoa.getEndereco_cidade()))
                                    .bairro(Normalizador.abreviar(Normalizador.norm(pessoa.getEndereco_bairro())))
                                    .uf(pessoa.getEndereco_uf())
                                    .endereco(criarEnderecoCompleto(pessoa,40)) //o endereco deve ter 40 caracteres por causa da api do banco do brasil
                                    .build();

        return CobrancaInput.builder()
                            .numeroConvenio(Long.valueOf(fatura.getConvenio().getNumeroContrato()))
                            .numeroCarteira(Integer.valueOf(fatura.getConvenio().getCarteira()))
                            .numeroVariacaoCarteira(Integer.valueOf(fatura.getConvenio().getVariacaoCarteira()))
                            .dataVencimento(converterData(fatura.getData_vencimento()))
                            .dataEmissao(converterData(LocalDate.now()))
                            .valorOriginal(fatura.getValor())
                            .indicadorAceiteTituloVencido("S")
                            .codigoAceite('N')
                            .codigoTipoTitulo(2)
                            .descricaoTipoTitulo("Duplicata Mercantil")
                            .indicadorPermissaoRecebimentoParcial('N')
                            .numeroTituloBeneficiario(fatura.getNumeroDocumento())
                            .numeroTituloCliente(criarNossoNumero(fatura))
                            .indicadorPix("S")
                            .desconto(desconto)
                            .jurosMora(jurosMora)
                            .multa(multa)
                            .pagador(pagamento)
                            .build();
    }

    private String converterData(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	private String criarNossoNumero(Fatura fatura) {
		// regra: "000" + numero contrato convenio + 10 algarismos com zeros a esquerda
		// utilizar como 10 algarismos o numero documento

		return String.format("%010d", Long.valueOf(fatura.getConvenio().getNumeroContrato())).concat(String.format("%010d", Long.valueOf(fatura.getNumeroDocumento())));
	}

    private String criarEnderecoCompleto(pcp_cliente pessoa, int tamanhoMaximoEndereco) {
		var enderecoCompleto = "";

		var logradouro = Normalizador.abreviar(Normalizador.norm(pessoa.getEndereco_logradouro())).concat(", ");
		var temComplemento = StringUtils.hasText(pessoa.getEndereco_complemento());
		var tamanhoDoNumero = pessoa.getEndereco_numero().length();
		var tamanhoDoLogradouro = logradouro.length();

		if (temComplemento) {
			tamanhoDoNumero += 2; // conta-se a virgula e o espaco

			var tamanhoTotal = tamanhoDoNumero + tamanhoDoLogradouro + pessoa.getEndereco_complemento().length();

			if (tamanhoTotal > tamanhoMaximoEndereco) {

				logradouro = logradouro.substring(0, (tamanhoTotal - tamanhoMaximoEndereco));

				enderecoCompleto = logradouro
						.concat(pessoa.getEndereco_numero().concat(", ").concat(pessoa.getEndereco_complemento()));
			}

		} else {

			var tamanhoTotal = tamanhoDoNumero + tamanhoDoLogradouro;

			if (tamanhoTotal > tamanhoMaximoEndereco) {
				logradouro = logradouro.substring(0, (tamanhoTotal - tamanhoMaximoEndereco));

				enderecoCompleto = logradouro.concat(pessoa.getEndereco_numero());
			} else {
				enderecoCompleto = logradouro.concat(pessoa.getEndereco_numero());
			}
		}

		return enderecoCompleto;
	}

}
