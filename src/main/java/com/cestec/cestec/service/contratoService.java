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
import com.cestec.cestec.repository.imovelRepository;
import com.cestec.cestec.repository.proprietarioRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.util.utilForm;

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
    private prjContratosCustomRepository contratosCustomRepository;

    @Autowired
    private funcionarioRepository funcionarioRepository;

    public String getTipoImovel(Integer codImovel) {
        switch (codImovel) {
            case 1:
                return "Apartamento";
            case 2:
                return "Casa";
            case 3:
                return "Terreno";
        }
        return "Tipo do imovel não encontrado";
    }

    public String getDescTipos(Integer tipo) {
        switch (tipo) {
            case 1:
                return "Aluguel";
            case 2:
                return "Venda";
        }
        return "Descricão não encontrada";
    }

    public List<?> buscarContratoGrid(Integer codprop, Integer codcliente){
        List<contratoDTO> contratos = contratosCustomRepository.buscarContratoGrid(codprop,codcliente);

        utilForm.initGrid();
        for (int i = 0; i < contratos.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(contratos.get(i).getCodcontrato().toString());
            utilForm.criarColuna(contratos.get(i).getCodcliente().toString());
            utilForm.criarColuna(contratos.get(i).getNomeCliente());
            utilForm.criarColuna(contratos.get(i).getCodproprietario().toString());
            utilForm.criarColuna(contratos.get(i).getNomeProp());
            utilForm.criarColuna(contratos.get(i).getCodimovel().toString());
            utilForm.criarColuna(getTipoImovel(contratos.get(i).getTipo()));
            utilForm.criarColuna(getDescTipos(contratos.get(i).getNegociacao()));
            utilForm.criarColuna(String.valueOf(contratos.get(i).getPreco()));
            utilForm.criarColuna(contratos.get(i).getDatinicio().toString());
            utilForm.criarColuna(contratos.get(i).getDatfinal().toString());
            utilForm.criarColuna(String.valueOf(contratos.get(i).getValor()));
            utilForm.criarColuna(contratos.get(i).getEndereco_bairro());
            utilForm.criarColuna(contratos.get(i).getCodcorretor().toString());
            utilForm.criarColuna(contratos.get(i).getTipo().toString());
            utilForm.criarColuna(getNomeByIdeusu(contratos.get(i).getCodcorretor()));
        }

        return utilForm.criarGrid();
    }

    public String getNomeByIdeusu(Integer id){
        return funcionarioRepository.findNameByUser(id);
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
                                                     1,
                                                     corretor, 
                                                     (float) contrato.getPreco(),
                                                     true);
        
        return contratoRepository.save(pcp_contrato);
   }
}
