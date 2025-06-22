package com.cestec.cestec.infra.pagamento.service;

import org.springframework.stereotype.Service;

import com.cestec.cestec.infra.pagamento.CobrancaInput;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import com.cestec.cestec.model.contasAPagar.Fatura;
import com.cestec.cestec.service.pagamento.geradorBoleto;

@Service
public class pdfBoletoRegService implements geradorBoleto{

    @Override
	public byte[] gerar(Fatura fatura, CobrancaInput cobranca) {
        var boleto = criarBoleto(fatura, cobranca);
        var gerador = new GeradorDeBoleto(boleto);
        
        return gerador.geraPDF();
    }
    
}
