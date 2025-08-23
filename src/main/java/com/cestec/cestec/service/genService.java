package com.cestec.cestec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.repository.generico.funcionarioRepository;

@Service
public class genService {
    @Autowired
    private funcionarioRepository funcionarioRepository;

    public String getNomeByIdeusu(String ideusu){
        return funcionarioRepository.findNomeByIdeusu(ideusu);
    }
}
