package com.cestec.cestec.controller.opr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.opr.opr002s;

@RestController
@RequestMapping("/opr002")
public class opr002c {
    @Autowired
    private opr002s opr002s;

    @GetMapping("/carregarGridChamados")
    public List<?> carregarGridChamados(@RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "somenteAtivo", required = false) Integer somenteAtivo){
        return opr002s.carregarGridChamados(ideusu, somenteAtivo);
    }
}
