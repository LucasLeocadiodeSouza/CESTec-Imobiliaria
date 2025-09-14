package com.cestec.cestec.controller.cri;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.service.genService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cestec.cestec.service.cri.cri004s;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cri004")
public class cri004c {
    
    @Autowired
    private cri004s cri004s;

    @Autowired
    private genService gen;

    @GetMapping("/buscarContratoGrid")
    public List<?> buscarContratoGrid(@RequestParam(value = "codprop", required = false)    Integer codprop,
                                      @RequestParam(value = "codcliente", required = false) Integer codcliente,
                                      @RequestParam(value = "tipimovel", required = false)  Integer tipimovel) {
        return cri004s.buscarContratoGrid(codprop,codcliente,tipimovel);
    }

    @GetMapping("/getOptionsImovel")
    public List<modelUtilForm> getOptionsImovel(@RequestParam(value = "codprop", required = false) Integer codprop){
        return cri004s.getOptionsImovel(codprop);
    }

    @GetMapping("/getValorImovel")
    public BigDecimal getValorImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel) {
        return cri004s.getValorImovel(codimovel);
    }
    
    @GetMapping("/getTipoContratoImovel")
    public String getTipoContratoImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel) {
        return cri004s.getTipoContratoImovel(codimovel);
    }

    @GetMapping("/getBuscaTipoImovel")
    public String getBuscaTipoImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel) {
        return cri004s.getBuscaTipoImovel(codimovel);
    }

    @GetMapping("/getProprietarioByImovel")
    public Integer getProprietarioByImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel) {
        return cri004s.getProprietarioByImovel(codimovel);
    }

    @PostMapping("/inserirAlterarContrato")
    public ResponseEntity<?> inserirAlterarContrato(@RequestParam(value = "codcontrato", required = false)    Integer codcontrato,
                                                    @RequestParam(value = "codcliente", required = false)     Integer codcliente, 
                                                    @RequestParam(value = "codprop", required = false)        Integer codprop, 
                                                    @RequestParam(value = "codimovel", required = false)      Integer codimovel, 
                                                    @RequestParam(value = "vlrnegoc", required = false)       BigDecimal vlrnegoc, 
                                                    @RequestParam(value = "ideusucorretor", required = false) String ideusucorretor, 
                                                    @RequestParam(value = "datini", required = false)         Date datini, 
                                                    @RequestParam(value = "datfim", required = false)         Date datfim,
                                                     HttpServletRequest request){
        try {
            cri004s.salvarContrato(codcontrato, codcliente, codprop, codimovel, vlrnegoc, ideusucorretor, datini, datfim, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao cadastrar o contrato: " + e.getMessage());
        }
    }

    @PostMapping("/cancelarContrato")
    public ResponseEntity<?> cancelarContrato(@RequestParam(value = "codcontrato", required = false)    Integer codcontrato,
                                              @RequestParam(value = "codimovel", required = false)      Integer codimovel,
                                              HttpServletRequest request){
        try {
            cri004s.cancelarContrato(codcontrato, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao cancelar o contrato: " + e.getMessage());
        }
    }
    
    @PostMapping("/enviarContratoAprovacao")
    public ResponseEntity<?> enviarContratoAprovacao(@RequestParam(value = "codcontrato", required = false)    Integer codcontrato,
                                                     @RequestParam(value = "codimovel", required = false)      Integer codimovel,
                                                     HttpServletRequest request){
        try {
            cri004s.enviarContratoAprovacao(codcontrato, codimovel, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao enviar o contrato para aprovação: " + e.getMessage());
        }
    }
}
