package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.comWindowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/home") // URL base para acessar
public class comWindowController {
    
    @Autowired
    private comWindowService comWindowService;
    
    @PostMapping("/testando")
    public String testando(@RequestBody testeRequest request) {
        return comWindowService.teste(request.getNome());
    }
}

class testeRequest {
    private String nome;

    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) {
         this.nome = nome;
    }
}