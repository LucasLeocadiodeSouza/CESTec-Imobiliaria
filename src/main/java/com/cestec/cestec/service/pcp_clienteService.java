package com.cestec.cestec.service;

import java.time.LocalDateTime;
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
            clienteAnalise.setDocumento(cliente.getDocumento());
            clienteAnalise.setEmail(cliente.getEmail());
            clienteAnalise.setEndereco_bairro(cliente.getEndereco_bairro());
            clienteAnalise.setEndereco_cep(cliente.getEndereco_cep());
            clienteAnalise.setEndereco_cidade(cliente.getEndereco_cidade());
            clienteAnalise.setEndereco_complemento(cliente.getEndereco_complemento());
            clienteAnalise.setEndereco_numero(cliente.getEndereco_numero());
            clienteAnalise.setEndereco_uf(cliente.getEndereco_uf());
            clienteAnalise.setEndereco_logradouro(cliente.getEndereco_logradouro());
            clienteAnalise.setNome(cliente.getNome());
            clienteAnalise.setNumtel(cliente.getNumtel());
        }else{ cliente.setCriado_em(LocalDateTime.now()); }


        cliente.setAtualizado_em(LocalDateTime.now());
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
