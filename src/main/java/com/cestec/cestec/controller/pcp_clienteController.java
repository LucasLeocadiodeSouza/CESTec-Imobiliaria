package com.cestec.cestec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.pcp_cliente;
import com.cestec.cestec.service.pcp_clienteService;

@RestController
@RequestMapping("/cliente")
public class pcp_clienteController {
    
    @Autowired
    private pcp_clienteService pcp_clienteService;

    @PostMapping("/salvarCliente")
    public pcp_cliente salvarCliente(@RequestBody pcp_cliente pcp_cliente){
        return pcp_clienteService.salvarClientes(pcp_cliente);
    }
    
    @GetMapping("/buscarClientes")
    public List<pcp_cliente> buscarCliente(){
        return pcp_clienteService.buscarClientes();
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
