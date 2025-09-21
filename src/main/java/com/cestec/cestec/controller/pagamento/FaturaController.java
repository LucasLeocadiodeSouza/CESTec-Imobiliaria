package com.cestec.cestec.controller.pagamento;

import org.springframework.http.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.infra.pagamento.CobrancaInput;
import com.cestec.cestec.model.contasAPagar.BoletoRegistrado;
import com.cestec.cestec.model.contasAPagar.Fatura;
import com.cestec.cestec.model.contasAPagar.SituacaoFat;
import com.cestec.cestec.model.contasAPagar.TipoFatura;
import com.cestec.cestec.model.contasAPagar.TipoPagamento;
import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.repository.cri.contratoRepository;
import com.cestec.cestec.repository.pagamento.contaRepository;
import com.cestec.cestec.repository.pagamento.convenioRepository;
import com.cestec.cestec.repository.pagamento.faturaRepository;
import com.cestec.cestec.service.pagamento.faturaService;
import org.springframework.web.bind.annotation.PostMapping;

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
	private faturaRepository faturaRepository;

	@Autowired
	private contratoRepository contratoRepository;

	@GetMapping("/{faturaId}")
	public CobrancaInput transformar(@PathVariable Long faturaId) {
		return faturaservice.transformarFaturaEmCobranca(faturaId);
	}

	@PostMapping("/registrarFatura")
	public ResponseEntity<?> registrarFatura(@RequestParam(value="codcontrato", required = true) Integer codcontrato) {
		try {
            pcp_contrato contrato = contratoRepository.findByCodContrato(codcontrato);
			
			Fatura fatura = new Fatura();
			var conta     = contarepository.findContaById( 1);
			var convenio  = conveniorepository.findConvenioById(1);
			var pessoa    = contrato.getPcp_cliente();

			//fatura.setNumeroDocumento("71900");

			Long sequenciaNmrDoc;
			Fatura ultimafatura = faturaRepository.findTopByOrderByIdDesc();

			if (ultimafatura == null) sequenciaNmrDoc = Long.valueOf("71909"); //Para desenvolvimento, Aparentemente a API do BB tem o numero 71900 de registros padroes
			else sequenciaNmrDoc = Long.valueOf(ultimafatura.getNumeroDocumento()) + 1L;

			fatura.setNumeroDocumento(String.format("%08d", sequenciaNmrDoc));
			fatura.setConta(conta);
			fatura.setConvenio(convenio);
			fatura.setPcp_cliente(pessoa);
			fatura.setPcp_contrato(contrato);
			fatura.setSituacao(SituacaoFat.NAO_PAGA);
			fatura.setTipo(TipoFatura.RECEITA);
			fatura.setTipoPagamento(TipoPagamento.BOLETO);
			fatura.setValor(contrato.getValorliberado());
			fatura.setData_vencimento(LocalDate.now().plusDays(15));
			fatura.setCriadoEm(LocalDateTime.now());
			fatura.setAtualizadoEm(LocalDateTime.now());

			faturaRepository.save(fatura);
			//faturaservice.criarFatura(fatura);

			faturaservice.registrarBoleto(fatura.getId());

			return ResponseEntity.ok().body("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao registrar Fatura: " + e.getMessage());
        }
	}
	
	@PostMapping("/registrarBoleto")
	public BoletoRegistrado registrarBoleto(@RequestParam(value="id", required = true) Long id) {
		return faturaservice.registrarBoleto(id);
	}

	@GetMapping(path = "/{faturaId}/boleto/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> gerarBoleto(@PathVariable Long faturaId) {
		byte[] bytesPdf = faturaservice.gerar(faturaId);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytesPdf);
	}
}

