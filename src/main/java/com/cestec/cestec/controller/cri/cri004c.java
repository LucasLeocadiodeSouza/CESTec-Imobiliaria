package com.cestec.cestec.controller.cri;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.cri.pcri001s;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/contrato")
public class cri004c {
    
    @Autowired
    private pcri001s pcri001s;

    @Autowired
    private genService gen;

    @GetMapping("/buscarContratoGrid")
    public List<?> buscarContratoGrid(@RequestParam(value = "codprop", required = false) Integer codprop,
                                      @RequestParam(value = "codcliente", required = false) Integer codcliente) {
        return pcri001s.buscarContratoGrid(codprop,codcliente);
    }

    @GetMapping("/{id}/getNomeByIdeusu")
    public String getNomeByIdeusu(@PathVariable Integer id) {
        return gen.getNomeByCodFunc(id);
    }
    
    // @PostMapping("/inserirAlterarContrato")
    // public pcp_contrato inserirAlterarContrato(@RequestBody contratoDTO contratoDTO) {
    //     return contratoService.salvarContrato(contratoDTO);
    // }
}
