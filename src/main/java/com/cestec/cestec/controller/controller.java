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
    
    @GetMapping("/contratosCadastroContrato") 
    public String contratosCadastroContrato() {
        return "wcr004";
    }
    
    @GetMapping("/contratosCadastroMetas") 
    public String contratosCadastroMetas() {
        return "wcr005";
    }

    @GetMapping("/fichaContrato") 
    public String fichaContrato() {
        return "wcr00401";
    }

    @GetMapping("/login") 
    public String login() {
        return "wlog";
    }
}
