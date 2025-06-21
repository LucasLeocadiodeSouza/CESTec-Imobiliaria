package com.cestec.cestec.controller.mrb;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.mrb.mrb002s;

@RestController
@RequestMapping("/mrb002")
public class mrb002c {

    @Autowired
    private mrb002s mrb002s;

    @GetMapping("/buscarTabelas")
    public List<?> buscarTabelas(@RequestParam(value = "nomeTabela", required = false) String nomeTabela){
        return mrb002s.buscarTabelas(nomeTabela);
    }

    @GetMapping("/buscarColunas")
    public List<?> buscarColunas(@RequestParam(value = "nomeTabela") String nomeTabela) throws SQLException{
        return mrb002s.buscarColunas(nomeTabela);
    }

    @GetMapping("/buscarIndexs")
    public List<?> buscarIndexs(@RequestParam(value = "nomeTabela") String nomeTabela) throws SQLException{
        return mrb002s.buscarIndexs(nomeTabela);
    }
}
