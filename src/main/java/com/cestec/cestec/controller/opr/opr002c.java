package com.cestec.cestec.controller.opr;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.opr.chamadoSolicDTO;
import com.cestec.cestec.service.opr.opr002s;

@RestController
@RequestMapping("/opr002")
public class opr002c {
    @Autowired
    private opr002s opr002s;

    @GetMapping("/getNomeUsuario")
    public String getNomeUsuario(@RequestParam(value = "ideusu", required = false) String ideusu){
        return opr002s.getNomeUsuario(ideusu);
    }

    @GetMapping("/getOptionsPrioridade")
    public List<?> getOptionsPrioridade(){
        return opr002s.getOptionsPrioridade();
    } 

    @PostMapping("/abrirSolicitacao")
    public ResponseEntity<?> abrirSolicitacao(@RequestBody chamadoSolicDTO solicitacao){
        return opr002s.abrirSolicitacao(solicitacao);
    }

    @PostMapping("/enviarSolicitacao")
    public ResponseEntity<?> enviarSolicitacao(@RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "idsolic", required = false) Integer idsolic){
        return opr002s.enviarSolicitacao(ideusu, idsolic);
    }

    @PostMapping("/direcionarSolic")
    public ResponseEntity<?> direcionarSolic(@RequestParam(value = "ideusu", required = false) String ideusu,@RequestParam(value = "ideusudirec", required = false) String ideusudirec, @RequestParam(value = "idsolic", required = false) Integer idsolic){
        return opr002s.direcionarSolic(ideusu,ideusudirec,idsolic);
    }

    @GetMapping("/carregarGridChamSolicitados")
    public List<?> carregarGridChamSolicitados(@RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "somenteAtivo", required = false) Integer somenteAtivo){
        return opr002s.carregarGridChamSolicitados(ideusu, somenteAtivo);
    }

    @GetMapping("/carregarGridChamados")
    public List<?> carregarGridChamados(@RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "somenteAtivo", required = false) Integer somenteAtivo, @RequestParam(value = "acao", required = false) String acao){
        return opr002s.carregarGridChamados(ideusu, somenteAtivo, acao);
    }
}
