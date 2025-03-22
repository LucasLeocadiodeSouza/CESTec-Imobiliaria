package com.cestec.cestec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.repository.contratoRepository;

@Service
public class wcr006s {
    
    @Autowired
    private contratoRepository contratoRepository;

    public List<contratoDTO> buscarContratoAprovacao(){
        return contratoRepository.buscarContratoAprovacao();
    }

}
