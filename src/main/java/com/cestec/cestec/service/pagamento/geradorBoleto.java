package com.cestec.cestec.service.pagamento;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import com.cestec.cestec.infra.pagamento.CobrancaInput;
import com.cestec.cestec.model.contasAPagar.Fatura;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;

public interface geradorBoleto {
    public byte[] gerar(Fatura fatura, CobrancaInput cobranca);

    default Boleto criarBoleto(Fatura fatura, CobrancaInput cobrancas){

        var instrucao1 = String.format("Após o vencimento cobrar Juros de %s por mês.", cobrancas.getJurosMora().getPorcentagem());
        var instrucao2 = String.format("Após o vencimento cobrar Multa de %s.", cobrancas.getMulta().getPorcentagem());
        var instrucao3 = String.format("Referente a Fatura n: %d.", fatura.getId());

        var boleto = Boleto.novoBoleto()
                        .comBanco(new BancoDoBrasil())
                        .comDatas(criarDatas(fatura))
                        .comBeneficiario(criarBeneficiario(fatura))
                        .comPagador(criarPagador(fatura, cobrancas))
                        .comValorBoleto(fatura.getValor())
                        .comNumeroDoDocumento(fatura.getNumeroDocumento())
                        .comEspecieDocumento("DM")
                        .comAceite(false)
                        .comLocaisDePagamento("Pagável em qual Banco até o vencimento")
                        .comInstrucoes(instrucao1,instrucao2,instrucao3);

        return boleto;
    }

    default Datas criarDatas(Fatura fatura){
        var vencimento = fatura.getData_vencimento();
        var criadoEm = fatura.getCriadoEm();
        var atualizaEm = fatura.getAtualizadoEm();

        var datas = Datas.novasDatas()
                         .comDocumento(criadoEm.getDayOfMonth(), criadoEm.getMonthValue(), criadoEm.getYear())
                         .comProcessamento(atualizaEm.getDayOfMonth(), atualizaEm.getMonthValue(), atualizaEm.getYear())
                         .comVencimento(vencimento.getDayOfMonth(), vencimento.getMonthValue(), vencimento.getYear());
        return datas;
    }

    default Pagador criarPagador(Fatura fatura, CobrancaInput cobranca){
        var endereco = Endereco.novoEndereco()
                               .comLogradouro(cobranca.getPagador().getEndereco())
                               .comBairro(cobranca.getPagador().getBairro())
                               .comCep(insereMascaraAoRetornarDocumento(cobranca.getPagador().getCep().toString()).concat(" "))
                               .comCidade(cobranca.getPagador().getCidade())
                               .comUf(cobranca.getPagador().getUf());

        var pagador = Pagador.novoPagador()
                             .comNome(cobranca.getPagador().getNome())
                             .comDocumento(insereMascaraAoRetornarDocumento(cobranca.getPagador().getNumeroInscricao()))
                             .comEndereco(endereco);

        return pagador;
    }

    default Beneficiario criarBeneficiario(Fatura fatura){
        var empresa  = fatura.getConta().getEmpresa();        
        var nomeBene = empresa.getRazaoSocial();
        var conta    = fatura.getConta();

        String nossoNumero = fatura.getNossoNumero().substring(0, 17);
        fatura.setNossoNumero(nossoNumero);

        var endereco = Endereco.novoEndereco()
                                .comLogradouro(empresa.getEndereco_logradouro().concat(", ").concat(empresa.getEndereco_numero()))
                                .comBairro(empresa.getEndereco_bairro())
                                .comCep(insereMascaraAoRetornarDocumento(empresa.getEndereco_cep().replaceAll("\\D", "")))
                                .comCidade(empresa.getEndereco_cidade())
                                .comUf(empresa.getEndereco_uf());

        var beneficiario = Beneficiario.novoBeneficiario()
                                       .comNomeBeneficiario(nomeBene)
                                       .comDocumento(insereMascaraAoRetornarDocumento(empresa.getCnpj()))
                                       .comNossoNumero(fatura.getNossoNumero())
                                       .comAgencia(conta.getAgencia())
                                       .comDigitoAgencia(conta.getDigitoAgencia())
                                       .comCodigoBeneficiario(conta.getConta())
                                       .comDigitoCodigoBeneficiario(conta.getDigitoConta())
                                       .comNumeroConvenio(fatura.getConvenio().getNumeroContrato())
                                       .comCarteira(fatura.getConvenio().getCarteira())
                                       .comEndereco(endereco);
        
        return beneficiario;
    }

    
    default String insereMascaraAoRetornarDocumento(String documento) {
		try {
			MaskFormatter mask = new MaskFormatter();
			mask.setValueContainsLiteralCharacters(false);
			if (documento.length() == 11) {
				mask.setMask("###.###.###-##");
			} else if(documento.length() == 8) {
				mask.setMask("##.###-###");
			} else {
				mask.setMask("###.###.###/####-##");
			}
			return mask.valueToString(documento);
		} catch (ParseException e) {
			throw new RuntimeException("Erro ao formatar documento.");
		}
	}
}
