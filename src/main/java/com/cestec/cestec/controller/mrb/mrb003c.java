package com.cestec.cestec.controller.mrb;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.funcionarioDTO;
import com.cestec.cestec.service.mrb.mrb003s;

@RestController
@RequestMapping("/mrb003")
public class mrb003c {

    @Autowired
    private mrb003s mrb003s;

    @GetMapping("/buscarNomeCargo")
    public String buscarNomeCargo(@RequestParam(value = "codcargo", required = false) Integer codcargo){
        return mrb003s.buscarNomeCargo(codcargo);
    }

    @GetMapping("/buscarNomeSetor")
    public String buscarNomeSetor(@RequestParam(value = "codsetor", required = false) Integer codsetor){
        return mrb003s.buscarNomeSetor(codsetor);
    }

    @GetMapping("/carregarGridFuncionarios")
    public List<?> carregarGridFuncionarios(@RequestParam(value = "ideusu", required = false) String ideusu){
        return mrb003s.carregarGridFuncionarios(ideusu);
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody funcionarioDTO funcionarioDTO, @RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "acao", required = false) String acao) {
        return mrb003s.cadastrarUsuario(funcionarioDTO, ideusu, acao.trim());
    }

}