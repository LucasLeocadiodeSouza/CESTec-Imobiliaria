package com.cestec.cestec.controller.mrb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.mrb.mrb001s;

@RestController
@RequestMapping("/mrb001")
public class mrb001c {
    
    @Autowired
    private mrb001s mrb001s;

    @GetMapping("/buscarAplicacoesGrid")
    public List<?> buscarAplicacoesGrid(@RequestParam(value="codapl", required = false) Integer codapl,
                                        @RequestParam(value = "codmodu", required = false) Integer codmodu,
                                        @RequestParam(value = "ideusu", required = false) String ideusu){
        return mrb001s.buscarAplicacoesGrid(codapl, codmodu, ideusu);
    }

}
