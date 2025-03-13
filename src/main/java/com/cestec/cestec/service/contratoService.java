package com.cestec.cestec.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.model.pcp_cliente;
import com.cestec.cestec.model.pcp_contrato;
import com.cestec.cestec.model.pcp_corretor;
import com.cestec.cestec.model.pcp_imovel;
import com.cestec.cestec.model.pcp_proprietario;
import com.cestec.cestec.repository.clienteRepository;
import com.cestec.cestec.repository.contratoRepository;
import com.cestec.cestec.repository.corretorRepository;
import com.cestec.cestec.repository.funcionarioRepository;
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
    private corretorRepository corretorRepository;

    @Autowired
    private funcionarioRepository funcionarioRepository;

    public List<contratoDTO> buscarContratoGrid(){
        return contratoRepository.buscarContratoGrid();
    }

    public String getNomeByIdeusu(String ideusu){
        return funcionarioRepository.findByUser(ideusu).getNome();
    }

   public pcp_contrato salvarContrato(contratoDTO contrato){
        pcp_imovel imovel             = imovelRepository.findByCodimovel(contrato.getCodimovel());
        pcp_cliente cliente           = clienteRepository.findByCodcliente(contrato.getCodcliente());
        pcp_proprietario proprietario = proprietarioRepository.findByCodproprietario(contrato.getCodproprietario());
        pcp_corretor corretor         = corretorRepository.findCorretorByIdeusu(contrato.getIdeusuCorretor());

        pcp_contrato pcp_contrato = new pcp_contrato(cliente,
                                                     imovel,
                                                     proprietario,
                                                     contrato.getDatinicio(),
                                                     contrato.getDatfinal(), 
                                                     Date.valueOf(LocalDate.now()),
                                                     corretor, 
                                                     (float) contrato.getPreco(),
                                                     true);
        
        return contratoRepository.save(pcp_contrato);
   }
}
