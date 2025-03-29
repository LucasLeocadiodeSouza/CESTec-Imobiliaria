package com.cestec.cestec.controller.pagamento;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.cestec.cestec.model.contasAPagar.Pessoa;
import com.cestec.cestec.service.pagamento.pessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/pessoa")
public class pessoaController {
    
    @Autowired
    private pessoaService pessoaService;

    @GetMapping("/listar")
    public List<Pessoa> listarPessoas() {
        return pessoaService.listarPessoas();
    }
    

}
