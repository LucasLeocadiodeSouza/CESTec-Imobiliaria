package com.cestec.cestec.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.model.pcp_cliente;
import com.cestec.cestec.model.pcp_contrato;
import com.cestec.cestec.model.pcp_contrato_tipo;
import com.cestec.cestec.model.pcp_imovel;
import com.cestec.cestec.model.pcp_proprietario;
import com.cestec.cestec.repository.clienteRepository;
import com.cestec.cestec.repository.contratoRepository;
import com.cestec.cestec.repository.contratoTipoRepository;
import com.cestec.cestec.repository.imovelRepository;
import com.cestec.cestec.repository.proprietarioRepository;

@Service
public class contratoService {
    
    @Autowired
    private contratoRepository contratoRepository;

    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private clienteRepository clienteRepository;

    @Autowired
    private imovelRepository imovelRepository;

    @Autowired
    private contratoTipoRepository contratoTipoRepository;

    public List<contratoDTO> buscarContratoGrid(){
        return contratoRepository.buscarContratoGrid();
    }

    /*
    public pcp_contrato saveContrato(pcp_contrato pcp_contrato, Integer codimovel){
        saveContratoTipo(pcp_contrato, codimovel); //colocar a codimovel por parametro que pega tudo de uma vez logo fds
        return contratoRepository.save(pcp_contrato);
    }

    public pcp_contrato_tipo saveContratoTipo(pcp_contrato pcp_contrato, Integer codimovel){
        pcp_imovel imovel = imovelRepository.findById(codimovel)
        .orElseThrow(() -> new RuntimeException("Imovel não encontrado com o código: " + codimovel)); 

        pcp_contrato_tipo tipo = new pcp_contrato_tipo();
        tipo.setPcp_contrato(pcp_contrato);
        tipo.setPcp_imovel(imovel);
        tipo.setDatiregistro(Date.valueOf(LocalDate.now()));

        return contratoTipoRepository.save(tipo);
    } */
}
