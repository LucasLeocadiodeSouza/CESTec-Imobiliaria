package com.cestec.cestec.controller.pga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.pagamento.pga001s;

@RestController
@RequestMapping("/pag001")
public class pag001c {
    @Autowired
    private pga001s pga001;

    @GetMapping("/buscarFaturaCliente")
    public List<?> buscarFaturaCliente(){
        return pga001.buscarFaturaCliente();
    }
}
