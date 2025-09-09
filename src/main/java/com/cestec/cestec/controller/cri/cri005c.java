package com.cestec.cestec.controller.cri;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.cri.cri005s;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/cri005")
public class cri005c {

    @Autowired
    private cri005s wcr005s;

    @GetMapping("/buscarContratoAprovacao")
    public List<?> buscarContratoAprovacao(@RequestParam(value=  "codprop", required = false)     Integer codprop,
                                           @RequestParam(value = "codcliente", required = false)  Integer codcliente,
                                           @RequestParam(value = "codcorretor", required = false) Integer codcorretor,
                                           @RequestParam(value = "tipimovel", required = false)   Integer tipimovel,
                                           @RequestParam(value = "acao", required = false)        Integer acao) {
        return wcr005s.buscarContratoAprovacao(codprop, codcliente, codcorretor, tipimovel, acao);
    }
    
    // @PostMapping("/aprovarReprovarContrato")
    // public ResponseEntity<?> aprovarReprovarContrato(@RequestBody pcp_contrato contrato) {
    //     if(contrato == null) {
    //         return ResponseEntity.badRequest().body("Dados do contrato não podem ser nulos");
    //     }
    
    //     return wcr006s.aprovarReprovarContrato(contrato);
    // }
}
