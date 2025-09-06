package com.cestec.cestec.controller.cri;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.cri.cri001s;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cri001")
public class cri001c {
    @Autowired
    private cri001s cri001s;

    @Autowired
    private genService gen;

    @GetMapping("/getTipoImovel")
    public String getTipoImovel(@RequestParam(value = "codImovel", required = false) Integer codImovel) {
        return cri001s.getTipoImovel(codImovel);
    }

    @PostMapping("/salvarImovel")
    public ResponseEntity<?> salvarImovel(@RequestBody pcp_imovel imovel, @RequestParam(value = "codprop", required = false) Integer codprop, HttpServletRequest request) {
        try {
            cri001s.salvarImovel(imovel, codprop, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao cadastrar imovel: " + e.getMessage());
        }
    }

    @PostMapping("/inativarImovel")
    public ResponseEntity<?> inativarImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel, HttpServletRequest request) {
        try {
            cri001s.inativarImovel(codimovel, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao inativar imovel: " + e.getMessage());
        }
    }

    @GetMapping("/getEnderecoImovel")
    public String getEnderecoImovel(@RequestParam(value = "codImovel", required = false) Integer codImovel) {
        return cri001s.getEnderecoImovel(codImovel);
    }

    @GetMapping("/getTipoContratoImovel")
    public String getTipoContratoImovel(@RequestParam(value = "codImovel", required = false) Integer codImovel) {
        return cri001s.getTipoContratoImovel(codImovel);
    }

    @GetMapping("/getValorImovel")
    public double getValorImovel(@RequestParam(value = "codImovel", required = false) Integer codImovel) {
        return cri001s.getValorImovel(codImovel);
    }

    @GetMapping("/buscarImoveis")
    public List<?> buscarImoveis(@RequestParam(value = "codcontrato", required = false) Integer codcontrato,
                                 @RequestParam(value = "codprop", required = false)     Integer codprop,
                                 @RequestParam(value = "tipimovel", required = false)   Integer tipimovel) {
        return cri001s.buscarImoveis(codcontrato,codprop,tipimovel);
    }

    @GetMapping("/getOptionsTpContrato")
    public List<modelUtilForm> getOptionsTpContrato() {
        return cri001s.getOptionsTpContrato();
    }

    @GetMapping("/getOptionsTpImovel")
    public List<modelUtilForm> getOptionsTpImovel() {
        return cri001s.getOptionsTpImovel();
    }
}
