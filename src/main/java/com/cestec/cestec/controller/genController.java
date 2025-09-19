package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.genService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/gen")
public class genController {
    @Autowired
    private genService gen;

    @GetMapping("/getDescricaoAplicacao")
    public ResponseEntity<?> getDescricaoAplicacao(@RequestParam(value = "codapl", required = false) Integer codapl){
        try {
            return ResponseEntity.ok(gen.getDescricaoAplicacao(codapl));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getDescricaoModulo")
    public ResponseEntity<?> getDescricaoModulo(@RequestParam(value = "codmod", required = false) Integer codmod){
        try {
            return ResponseEntity.ok(gen.getDescricaoModulo(codmod));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getProgInicial")
    public ResponseEntity<?> getProgInicial(@RequestParam(value = "codapl", required = false) Integer codapl){
        try {
            return ResponseEntity.ok(gen.getProgInicial(codapl));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getNomeByIdeusu")
    public ResponseEntity<?> getNomeByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        try {
            return ResponseEntity.ok(gen.getNomeByIdeusu(ideusu));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/usuarioTemAcessoAplicacao")
    public ResponseEntity<?> usuarioTemAcessoAplicacao(@RequestParam(value = "codmod", required = false) Integer codmod, @RequestParam(value = "codapl", required = false) Integer codapl, HttpServletRequest request){
        try {
            return ResponseEntity.ok(gen.usuarioTemAcessoAplicacao(codmod, codapl, gen.getUserName(request)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getNomeByCodFunc")
    public ResponseEntity<?> getNomeByCodFunc(@RequestParam(value = "codfunc", required = false) Integer codfunc){
        try {
            return ResponseEntity.ok(gen.getNomeByCodFunc(codfunc));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getCodFuncByIdeusu")
    public ResponseEntity<?> getCodFuncByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        try {
            return ResponseEntity.ok(gen.getCodFuncByIdeusu(ideusu));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getNomeCorretorByIdeusu")
    public ResponseEntity<?> getNomeCorretorByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        try {
            return ResponseEntity.ok(gen.getNomeCorretorByIdeusu(ideusu));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/findCargoByIdeusu")
    public ResponseEntity<?> findCargoByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        try {
            return ResponseEntity.ok(gen.findCargoByIdeusu(ideusu));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    } 

    @GetMapping("/findSetorByIdeusu")
    public ResponseEntity<?> findSetorByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        try {
            return ResponseEntity.ok(gen.findSetorByIdeusu(ideusu));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }  
    }

    @GetMapping("/findCodSetorByIdeusu")
    public ResponseEntity<?> findCodSetorByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        try {
            return ResponseEntity.ok(gen.findCodSetorByIdeusu(ideusu));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        } 
    }

    @GetMapping("/getNomeProp")
    public ResponseEntity<?> getNomeProp(@RequestParam(value = "codprop", required = false) Integer codProprietario){
        try {
            return ResponseEntity.ok(gen.getNomeProp(codProprietario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getNomeCliente")
    public ResponseEntity<?> getNomeCliente(@RequestParam(value = "codcli", required = false) Integer codcli){
        try {
            return ResponseEntity.ok(gen.getNomeCliente(codcli));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getEnderecoImovel")
    public ResponseEntity<?> getEnderecoImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel){
        try {
            return ResponseEntity.ok(gen.getEnderecoImovel(codimovel));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }
}
