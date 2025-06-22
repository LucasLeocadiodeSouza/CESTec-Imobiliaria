package com.cestec.cestec.controller.pagamento;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.infra.pagamento.CobrancaInput;
import com.cestec.cestec.model.contasAPagar.BoletoRegistrado;
import com.cestec.cestec.model.contasAPagar.Fatura;
import com.cestec.cestec.repository.clienteRepository;
import com.cestec.cestec.repository.pagamento.contaRepository;
import com.cestec.cestec.repository.pagamento.convenioRepository;
import com.cestec.cestec.service.pagamento.faturaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

	@Autowired
	private faturaService faturaservice;

    @Autowired
	private contaRepository contarepository;

	@Autowired
	private convenioRepository conveniorepository;

	@Autowired
	private clienteRepository clienterepository;

	@GetMapping("/{faturaId}")
	public CobrancaInput transformar(@PathVariable Long faturaId) {
		return faturaservice.transformarFaturaEmCobranca(faturaId);
	}

	@PostMapping("/registrarFatura/{clienteId}")
	public CobrancaInput registrarFatura(@PathVariable Integer clienteId, @RequestBody Fatura fatura) {
		var conta    = contarepository.findContaById( 1);
		var convenio = conveniorepository.findConvenioById(1);
		var pessoa   = clienterepository.findByCodcliente(clienteId);

		fatura.setNumeroDocumento("71900");
		fatura.setConta(conta);
		fatura.setConvenio(convenio);
		fatura.setPcp_cliente(pessoa);

		return faturaservice.criarFatura(fatura);
	}
	
	@PostMapping("/registrarBoleto/{id}")
	public BoletoRegistrado registrarBoleto(@PathVariable Long id) {
		return faturaservice.registrarBoleto(id);
	}

	@GetMapping(path = "/{faturaId}/boleto/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> gerarBoleto(@PathVariable Long faturaId) {
		System.out.println("service gerar");
		byte[] bytesPdf = faturaservice.gerar(faturaId);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytesPdf);
	}
}

