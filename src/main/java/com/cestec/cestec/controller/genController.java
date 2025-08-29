package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.genService;

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
}
