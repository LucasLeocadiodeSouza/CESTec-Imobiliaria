package com.cestec.cestec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.model.pcp_contrato;
import com.cestec.cestec.service.contratoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/contrato")
public class contratoController {
    
    @Autowired
    private contratoService contratoService;

    @GetMapping("/buscarContratoGrid")
    public List<contratoDTO> buscarContratoGrid() {
        return contratoService.buscarContratoGrid();
    }
    
    /*
    @PostMapping("/{cod}/inserirAlterarContrato")
    public pcp_contrato inserirAlterarContrato(@RequestBody pcp_contrato pcp_contrato, @PathVariable Integer codimovel) {        
        return contratoService.saveContrato(pcp_contrato, codimovel);
    } */

    

}
