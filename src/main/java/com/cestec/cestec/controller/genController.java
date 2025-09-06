package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String getDescricaoAplicacao(@RequestParam(value = "codapl", required = false) Integer codapl){
        return gen.getDescricaoAplicacao(codapl);
    }

    @GetMapping("/getDescricaoModulo")
    public String getDescricaoModulo(@RequestParam(value = "codmod", required = false) Integer codmod){
        return gen.getDescricaoModulo(codmod);
    }

    @GetMapping("/getNomeByIdeusu")
    public String getNomeByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        return gen.getNomeByIdeusu(ideusu);
    }

    @GetMapping("/usuarioTemAcessoAplicacao")
    public Boolean usuarioTemAcessoAplicacao(@RequestParam(value = "codmod", required = false) Integer codmod, @RequestParam(value = "codapl", required = false) Integer codapl, HttpServletRequest request){
        return gen.usuarioTemAcessoAplicacao(codmod, codapl, gen.getUserName(request));
    }

    @GetMapping("/getNomeByCodFunc")
    public String getNomeByCodFunc(@RequestParam(value = "codfunc", required = false) Integer codfunc){
        return gen.getNomeByCodFunc(codfunc);
    }

    @GetMapping("/findCargoByIdeusu")
    public String findCargoByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        return gen.findCargoByIdeusu(ideusu);
    }

    @GetMapping("/findSetorByIdeusu")
    public String findSetorByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        return gen.findSetorByIdeusu(ideusu);
    }

    @GetMapping("/findCodSetorByIdeusu")
    public String findCodSetorByIdeusu(@RequestParam(value = "ideusu", required = false) String ideusu){
        return gen.findCodSetorByIdeusu(ideusu);
    }

    @GetMapping("/getNomeProp")
    public String getNomeProp(@RequestParam(value = "codprop", required = false) Integer codProprietario){
        return gen.getNomeProp(codProprietario);
    }

    @GetMapping("/getNomeCliente")
    public String getNomeCliente(@RequestParam(value = "codcli", required = false) Integer codcli){
        return gen.getNomeCliente(codcli);
    }
}
