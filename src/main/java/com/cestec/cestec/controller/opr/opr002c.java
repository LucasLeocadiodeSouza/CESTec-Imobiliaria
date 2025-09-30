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

    @GetMapping("/getOptionsComplex")
    public List<?> getOptionsComplex(){
        return opr002s.getOptionsComplex();
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
    public ResponseEntity<?> direcionarSolic(@RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "ideusudirec", required = false) String ideusudirec, @RequestParam(value = "complex", required = false) Integer complex, @RequestParam(value = "idsolic", required = false) Integer idsolic){
        return opr002s.direcionarSolic(ideusu,ideusudirec,complex,idsolic);
    }

    @PostMapping("/incluirVersionamento")
    public ResponseEntity<?> incluirVersionamento(@RequestParam(value = "ideusu", required = false) String ideusu,
                                                  @RequestParam(value = "idsolic", required = false) Integer idsolic,
                                                  @RequestParam(value = "merge", required = false) Integer merge,
                                                  @RequestParam(value = "branch", required = false) String branch,
                                                  @RequestParam(value = "prog", required = false) String prog){
        return opr002s.incluirVersionamento(ideusu, idsolic, merge, branch, prog);
    }

    @PostMapping("/finalizarSolicitacao")
    public ResponseEntity<?> finalizarSolicitacao(@RequestParam(value = "ideusu", required = false) String ideusu,
                                                  @RequestParam(value = "idsolic", required = false) Integer idsolic,
                                                  @RequestParam(value = "obs", required = false) String obs){
        return opr002s.finalizarSolicitacao(ideusu, obs, idsolic);
    }

    @PostMapping("/iniciarSolicitacao")
    public ResponseEntity<?> iniciarSolicitacao(@RequestParam(value = "ideusu", required = false) String ideusu,
                                                @RequestParam(value = "idsolic", required = false) Integer idsolic){
        return opr002s.iniciarSolicitacao(ideusu, idsolic);
    }

    @GetMapping("/carregarGridChamSolicitados")
    public List<?> carregarGridChamSolicitados(@RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "somenteAtivo", required = false) Integer somenteAtivo){
        return opr002s.carregarGridChamSolicitados(ideusu, somenteAtivo);
    }

    @GetMapping("/carregarGridChamados")
    public List<?> carregarGridChamados(@RequestParam(value = "ideusu", required = false) String ideusu, @RequestParam(value = "somenteAtivo", required = false) Integer somenteAtivo, @RequestParam(value = "acao", required = false) String acao){
        return opr002s.carregarGridChamados(ideusu, somenteAtivo, acao);
    } 

    @GetMapping("/carregarGridversionamento")
    public List<?> carregarGridversionamento(@RequestParam(value = "idsolic", required = false) Integer idsolic){
        return opr002s.carregarGridversionamento(idsolic);
    }
}
