package com.cestec.cestec.controller.pga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.pagamento.pga00102s;

@RestController
@RequestMapping("/pag001")
public class pag00102c {
    @Autowired
    private pga00102s pga00102;

    @GetMapping("/getValorLiberadoContrato")
    public ResponseEntity<?> getValorLiberadoContrato(@RequestParam(value = "codcontrato", required = false) Integer codcontrato){
        try {
            return ResponseEntity.ok(pga00102.getValorLiberadoContrato(codcontrato));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getValorDescontoContrato")
    public ResponseEntity<?> getValorDescontoContrato(@RequestParam(value = "codcontrato", required = false) Integer codcontrato){
        try {
            return ResponseEntity.ok(pga00102.getValorDescontoContrato(codcontrato));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/buscarFaturaCliente")
    public List<?> buscarFaturaCliente(){
        return pga00102.buscarFaturaCliente();
    }
}
