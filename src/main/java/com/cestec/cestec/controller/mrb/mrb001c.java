package com.cestec.cestec.controller.mrb;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.aplicacaoDTO;
import com.cestec.cestec.service.mrb.mrb001s;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/mrb001")
public class mrb001c {
    
    @Autowired
    private mrb001s mrb001s;

    @GetMapping("/buscarAplicacoesGrid")
    public List<?> buscarAplicacoesGrid(@RequestParam(value="codapl", required = false) Integer codapl,
                                        @RequestParam(value = "codmodu", required = false) Integer codmodu,
                                        @RequestParam(value = "ideusu", required = false) String ideusu){
        return mrb001s.buscarAplicacoesGrid(codapl, codmodu, ideusu);
    }

    @PostMapping("/cadastrarAplicacao")
    public ResponseEntity<?> cadastrarAplicacao(@RequestParam(value="indcadastro", required = false)  Boolean indcadastro,
                                                @RequestParam(value="indliberacao", required = false) Boolean indliberacao,
                                                @RequestParam(value="indanalise", required = false)   Boolean indanalise,
                                                @RequestParam(value="indgestao", required = false)    Boolean indgestao,
                                                @RequestBody aplicacaoDTO aplicacao) {
        return mrb001s.cadastrarAplicacao(aplicacao, indcadastro, indliberacao, indanalise, indgestao);
    }

    @GetMapping("/getDescricaoModulo")
    public String getDescricaoModulo(@RequestParam(value = "codmodulo") Integer codmodulo) {
        return mrb001s.getDescricaoModulo(codmodulo);
    }
    
    @GetMapping("/buscarRoleAcess")
    public List<aplicacaoDTO> buscarRoleAcess(){
        return mrb001s.buscarAllRoles().stream()
                                       .map(aplicacaodto -> new aplicacaoDTO(aplicacaodto.getId(), aplicacaodto.getId() + " - " + aplicacaodto.getDescricao()))
                                       .toList();
    }
}