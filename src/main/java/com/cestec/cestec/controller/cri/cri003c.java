package com.cestec.cestec.controller.cri;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.cri.pcp_cliente;
import com.cestec.cestec.service.cri.cri003s;

@RestController
@RequestMapping("/cliente")
public class cri003c {
    
    @Autowired
    private cri003s pcp_clienteService;

    @PostMapping("/salvarCliente")
    public ResponseEntity<?> salvarCliente(@RequestBody pcp_cliente pcp_cliente){
        return pcp_clienteService.salvarClientes(pcp_cliente);
    }
    
    @GetMapping("/buscarClientes")
    public List<?> buscarCliente(@RequestParam(value = "codcliente", required = false) Integer codcliente){
        return pcp_clienteService.buscarClientes(codcliente);
    }

    @GetMapping("/{codcliente}/findNomeClienteById")
    public String findNomeClienteById(@PathVariable Integer codcliente){
        return pcp_clienteService.findNomeClienteById(codcliente);
    }

    @PostMapping("/{index}/buscarClienteGrid")
    public pcp_cliente buscarClienteGrid(@PathVariable Integer index) {
        return pcp_clienteService.buscarClienteGrid(index);
    }
}
