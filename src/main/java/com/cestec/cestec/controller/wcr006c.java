package com.cestec.cestec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.service.contratoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/wcr006c")
public class wcr006c {
    
    @Autowired
    private contratoService contratoService;

    @GetMapping("/buscarContratoAprovacao")
    public List<contratoDTO> buscarContratoAprovacao() {
        return contratoService.buscarContratoGrid();
    }
    

}
