package com.cestec.cestec.controller.mrb;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.mrb.mrb003s;

@RestController
@RequestMapping("/mrb003")
public class mrb003c {

    @Autowired
    private mrb003s mrb003s;

    @GetMapping("/carregarGridFucnionarios")
    public List<?> carregarGridFucnionarios(@RequestParam(value = "ideusu", required = false) String ideusu){
        return mrb003s.carregarGridFucnionarios(ideusu);
    }

}