package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.modulosRepository;

@Controller
public class controller {

    @Autowired
    private aplicacoesRepository aplicacoesrepo;

    @Autowired
    private modulosRepository modulosrepo;

    @GetMapping("/home") 
    public String home() {
        return "comWindow";
    }

    @GetMapping("/buscarPath/{codapl}")
    public String adapterGetHtmlFile(@PathVariable Integer codapl) {
        String aplicacao = aplicacoesrepo.findByCodApl(codapl).getArquivo_inic();

        Integer modulo = aplicacoesrepo.findByCodApl(codapl).getModulo().getId();

        String indmodulo = modulosrepo.findByIdModulos(modulo).getInd();

        return indmodulo + "/" + aplicacao;
    }

    @GetMapping("/login") 
    public String login() {
        return "wlog";
    }

    @GetMapping("/zoom") 
    public String zoom() {
        return "wzoom";
    }

    @GetMapping("/cabecalho") 
    public String cabecalho() {
        return "comCabecalho";
    }
}
