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

    @GetMapping("/{ideusu}/getNomeByIdeusu")
    public String getNomeByIdeusu(@PathVariable String ideusu) {
        System.out.println("ideusu " + ideusu);
        System.out.println("aa " + contratoService.getNomeByIdeusu(ideusu));
        return contratoService.getNomeByIdeusu(ideusu);
    }

    @GetMapping("/{index}/buscarContratoGrid")
    public contratoDTO buscarContratoLinha(@PathVariable Integer index) {
        return contratoService.buscarContratoGrid().get(index);
    }
    
    @PostMapping("/inserirAlterarContrato")
    public pcp_contrato inserirAlterarContrato(@RequestBody contratoDTO contratoDTO) {
        return contratoService.salvarContrato(contratoDTO);
    }
}
