package com.cestec.cestec.controller.pagamento;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.infra.pagamento.CobrancaInput;
import com.cestec.cestec.model.contasAPagar.BoletoRegistrado;
import com.cestec.cestec.model.contasAPagar.Cobranca;
import com.cestec.cestec.model.contasAPagar.Conta;
import com.cestec.cestec.model.contasAPagar.Fatura;
import com.cestec.cestec.repository.clienteRepository;
import com.cestec.cestec.repository.pagamento.contaRepository;
import com.cestec.cestec.repository.pagamento.convenioRepository;
import com.cestec.cestec.repository.pagamento.faturaRepository;
import com.cestec.cestec.service.pagamento.faturaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

	@Autowired
	private faturaService service;

	@GetMapping("/{faturaId}")
	public CobrancaInput transformar(@PathVariable Long faturaId) {
		return service.transformarFaturaEmCobranca(faturaId);
	}

	@PostMapping("/registrarFatura/{clienteId}")
	public Fatura registrarFatura(@PathVariable Integer clienteId, @RequestBody Fatura fatura) {
		return service.salvarFaturaNoBanco(clienteId, fatura);
	}
	
	@PostMapping("/registrarBoleto/{id}")
	public BoletoRegistrado registrarBoleto(@PathVariable Long id, @RequestBody Cobranca cobranca) {
		return service.registrarBoleto(id, cobranca);
	}

	@GetMapping(path = "/{faturaId}/boleto/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> gerarBoleto(@PathVariable Long faturaId) {
		byte[] bytesPdf = service.gerar(faturaId);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytesPdf);
	}
}

