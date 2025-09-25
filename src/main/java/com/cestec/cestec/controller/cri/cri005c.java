package com.cestec.cestec.controller.cri;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.cri.cri005s;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/cri005")
public class cri005c {

    @Autowired
    private cri005s wcr005s;

    @Autowired
    private genService gen;

    @GetMapping("/buscarImagensImovel")
    public ResponseEntity<?> buscarImagensImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel){
        try {
            return ResponseEntity.ok(wcr005s.buscarImagensImovel(codimovel));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao inativar imovel: " + e.getMessage());
        }
    }

    @GetMapping("/buscarContratoAprovacao")
    public List<?> buscarContratoAprovacao(@RequestParam(value=  "codprop", required = false)     Integer codprop,
                                           @RequestParam(value = "codcliente", required = false)  Integer codcliente,
                                           @RequestParam(value = "codcorretor", required = false) Integer codcorretor,
                                           @RequestParam(value = "tipimovel", required = false)   Integer tipimovel,
                                           @RequestParam(value = "acao", required = false)        Integer acao) {
        return wcr005s.buscarContratoAprovacao(codprop, codcliente, codcorretor, tipimovel, acao);
    }
    
    @PostMapping("/aprovarReprovarContrato")
    public ResponseEntity<?> aprovarReprovarContrato(@RequestParam(value=  "codcontrato", required = false)   Integer codcontrato,
                                                     @RequestParam(value = "valorliberado", required = false) BigDecimal valorliberado,
                                                     @RequestParam(value = "observacao", required = false)    String observacao,
                                                     @RequestParam(value = "acao", required = false)          String acao,
                                                     HttpServletRequest request){
        
        try {
            wcr005s.aprovarReprovarContrato(codcontrato, valorliberado, observacao, acao, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao cancelar o contrato: " + e.getMessage());
        }
    }
}
