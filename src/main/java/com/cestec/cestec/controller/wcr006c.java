package com.cestec.cestec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.contratoService;
import com.cestec.cestec.service.wcr006s;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/wcr006c")
public class wcr006c {
    
    @Autowired
    private contratoService contratoService;

    @Autowired
    private wcr006s wcr006s;

    @GetMapping("/buscarContratoAprovacao")
    public List<?> buscarContratoAprovacao() {
        return wcr006s.buscarContratoAprovacao();
    }
    

}
