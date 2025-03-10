package com.cestec.cestec.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.pcp_cliente;
import com.cestec.cestec.repository.clienteRepository;

@Service
public class pcp_clienteService {
    
    @Autowired
    private clienteRepository clienteRepository;

    public pcp_cliente salvarClientes(pcp_cliente cliente){
        pcp_cliente clienteAnalise = clienteRepository.findByCodcliente(cliente.getCodcliente());

        if(clienteAnalise != null){
            clienteAnalise.setCpf(cliente.getCpf());
            clienteAnalise.setEmail(cliente.getEmail());
            clienteAnalise.setEndereco(cliente.getEndereco());
            clienteAnalise.setNome(cliente.getNome());
            clienteAnalise.setNumtel(cliente.getNumtel());
        };
        return clienteRepository.save(cliente);
    }

    public List<pcp_cliente> buscarClientes(){
        return clienteRepository.findAll();
    }

    public pcp_cliente buscarClienteGrid(Integer index){
        return clienteRepository.findAll().get(index);
    }

    public String findNomeClienteById(Integer codcliente){
        return clienteRepository.findById(codcliente).get().getNome();
    }
}
