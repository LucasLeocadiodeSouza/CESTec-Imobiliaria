package com.cestec.cestec.controller.cri;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.service.cri.cri004s;
import com.cestec.cestec.service.cri.cri006s;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/wcr006c")
public class cri006c {
    
    @Autowired
    private cri004s contratoService;

    @Autowired
    private cri006s wcr006s;

    @GetMapping("/buscarContratoAprovacao")
    public List<?> buscarContratoAprovacao(@RequestParam(value="codprop", required = false) Integer codprop,
                                           @RequestParam(value = "codcliente", required = false) Integer codcliente,
                                           @RequestParam(value = "codcorretor", required = false) Integer codcorretor, 
                                           @RequestParam(value = "acao", required = false) Integer acao) {
        return wcr006s.buscarContratoAprovacao(codprop, codcliente, codcorretor, acao);
    }
    
    @PostMapping("/aprovarReprovarContrato")
    public ResponseEntity<?> aprovarReprovarContrato(@RequestBody pcp_contrato contrato) {
        if(contrato == null) {
            return ResponseEntity.badRequest().body("Dados do contrato não podem ser nulos");
        }
    
        return wcr006s.aprovarReprovarContrato(contrato);
    }
}
