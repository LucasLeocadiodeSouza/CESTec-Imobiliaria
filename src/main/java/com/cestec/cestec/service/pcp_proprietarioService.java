package com.cestec.cestec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.pcp_imovel;
import com.cestec.cestec.model.pcp_proprietario;
import com.cestec.cestec.repository.imovelRepository;
import com.cestec.cestec.repository.proprietarioRepository;

@Service
public class pcp_proprietarioService {
    
    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private imovelRepository imovelRepository;

    public String validaProprietario(pcp_proprietario proprietario){
        if(proprietario.getCpf() == ""){
            return "Proprietario Não pode ser cadastrado sem um CPF";
        }
        if(proprietario.getEmail() == ""){
            return "Deve ser Preenchido o Campo Email do proprietario";
        }
        if(proprietario.getNome() == ""){
            return "Deve ser Preenchido o Campo Nome do proprietario";
        }
        if(proprietario.getNumtel() == ""){
            return "Deve ser informado o Numero de telefone do proprietario";
        }
        return "OK";
    }

    public ResponseEntity<?> salvarProprietario(pcp_proprietario pcp_proprietario) {
        String validacao = validaProprietario(pcp_proprietario);        
        if (!validacao.equals("OK")) {
            return ResponseEntity.badRequest().body(validacao);
        }        
        pcp_proprietario salvo = proprietarioRepository.save(pcp_proprietario);
        return ResponseEntity.ok(salvo);
    }

    public pcp_imovel salvarImovel(pcp_imovel imovel, Integer codproprietario) {
        pcp_proprietario pcp_proprietario = proprietarioRepository.findById(codproprietario)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));

        imovel.setPcp_proprietario(pcp_proprietario);
        return imovelRepository.save(imovel);
    }

    public List<pcp_proprietario> listarProprietarios() {
        return proprietarioRepository.findAll();
    }

    public List<pcp_imovel> listarImoveisPorProprietario(Integer codProprietario) {
        return imovelRepository.findByProprietario(codProprietario);
    }
}
