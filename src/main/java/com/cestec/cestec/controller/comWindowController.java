package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.comWindowService;

@RestController
@RequestMapping("/home") // URL base para acessar
public class comWindowController {
    
    @Autowired
    private comWindowService comWindow;

    /*
    @RestController
    @RequestMapping("/contratos")
    public class ContratoController {
        
        @Autowired
        private ContratoService contratoService;

        @PostMapping("/gerar")
        public String gerarContrato(@RequestBody ContratoRequest request) {
            return contratoService.gerarContrato(request.getNome(), request.getValor());
        }
    }

    // Classe DTO para receber os dados do input
    class ContratoRequest {
        private String nome;
        private double valor;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public double getValor() { return valor; }
        public void setValor(double valor) { this.valor = valor; }
    }*/
}