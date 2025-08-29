package com.cestec.cestec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.generico.modulosRepository;

@Service
public class genService {
    @Autowired
    private funcionarioRepository funcionarioRepository;

    @Autowired
    private modulosRepository modulosRepository;

    @Autowired
    private aplicacoesRepository aplicacoesRepository;

    public String getNomeByIdeusu(String ideusu){
        return funcionarioRepository.findNomeByIdeusu(ideusu);
    }

    public String findCargoByIdeusu(String ideusu){
        return funcionarioRepository.findCargoByIdeusu(ideusu);
    }

    public String findSetorByIdeusu(String ideusu){
        return funcionarioRepository.findSetorByIdeusu(ideusu);
    } 

    public String findCodSetorByIdeusu(String ideusu){
        return funcionarioRepository.findCodSetorByIdeusu(ideusu);
    }

    public String getDescricaoModulo(Integer codmodulo){
        return modulosRepository.findByIdModulos(codmodulo).getDescricao();
    } 

    public String getDescricaoAplicacao(Integer codmodulo){
        return aplicacoesRepository.findByCodApl(codmodulo).getDescricao();
    }
}
