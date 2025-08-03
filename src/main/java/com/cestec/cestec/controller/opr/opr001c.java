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

import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.opr.agendamentoDTO;
import com.cestec.cestec.service.opr.opr001s;

@RestController
@RequestMapping("/opr001")
public class opr001c {
    @Autowired
    private opr001s opr001s;

    @GetMapping("/carregarGridFucnionarios")
    public List<?> carregarGridFucnionarios(@RequestParam(value = "codagend", required = false) Integer codagend, @RequestParam(value = "nomefunc", required = false) String nomefunc, @RequestParam(value = "acao", required = false) String acao){
        return opr001s.carregarGridFucnionarios(codagend, nomefunc, acao);
    }

    @GetMapping("/carregarGridSetores")
    public List<?> carregarGridSetores(@RequestParam(value = "codagend", required = false) Integer codagend, @RequestParam(value = "nomeSetor", required = false) String nomeSetor, @RequestParam(value = "acao", required = false) String acao){
        return opr001s.carregarGridSetores(codagend, nomeSetor, acao);
    }

    @GetMapping("/carregarGridCargos")
    public List<?> carregarGridCargos(@RequestParam(value = "codagend", required = false) Integer codagend, @RequestParam(value = "nomeCargo", required = false) String nomeCargo, @RequestParam(value = "acao", required = false) String acao){
        return opr001s.carregarGridCargos(codagend, nomeCargo, acao);
    }

    @GetMapping("/carregarGridAgendamentos")
    public List<?> carregarGridAgendamentos(@RequestParam(value = "codfunc", required = false) Integer codfunc, @RequestParam(value = "codcargo", required = false) Integer codcargo, @RequestParam(value = "codsetor", required = false) Integer codsetor){
        return opr001s.carregarGridAgendamentos(codfunc, codcargo, codsetor);
    }

    @PostMapping("/cadastrarAgendamento")
    public ResponseEntity<?> cadastrarAgendamento(@RequestBody agendamentoDTO agendamento){
        return opr001s.cadastrarAgendamento(agendamento);
    }

    @PostMapping("/vincularAgendamentoFunc")
    public ResponseEntity<?> vincularAgendamentoFunc(@RequestBody agendamentoDTO agendamento){
        return opr001s.vincularAgendamentoFunc(agendamento);
    }

    @GetMapping("/getOptionsMotivo")
    public List<modelUtilForm> getOptionsMotivo(){
        return opr001s.getOptionsMotivo();
    }

    @GetMapping("/getOptionsCargo")
    public List<modelUtilForm> getOptionsCargo(){
        return opr001s.getOptionsCargo();
    }
}
