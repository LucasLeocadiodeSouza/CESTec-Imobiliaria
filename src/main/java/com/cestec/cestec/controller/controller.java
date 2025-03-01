package com.cestec.cestec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {
    @GetMapping("/home") 
    public String home() {
        return "comWindow";
    }

    @GetMapping("/contratosCadastro") 
    public String contratosCadastro() {
        return "wcr001";
    }

    @GetMapping("/contratosCadastroPropri") 
    public String contratosCadastroPropri() {
        return "wcr002";
    }

    @GetMapping("/contratosCadastroClientes") 
    public String contratosCadastroClientes() {
        return "wcr003";
    }
}
