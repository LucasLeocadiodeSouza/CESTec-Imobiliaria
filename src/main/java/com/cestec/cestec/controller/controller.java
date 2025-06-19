package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cestec.cestec.repository.generico.aplicacoesRepository;

@Controller
public class controller {

    @Autowired
    private aplicacoesRepository aplicacoesrepo;

    @GetMapping("/home") 
    public String home() {
        return "comWindow";
    }

    @GetMapping("/buscarPath/{codapl}")
    public String adapterGetHtmlFile(@PathVariable Integer codapl) {
        return aplicacoesrepo.findByCodApl(codapl).getArquivo_inic();
    }

    @GetMapping("/login") 
    public String login() {
        return "wlog";
    }
}
