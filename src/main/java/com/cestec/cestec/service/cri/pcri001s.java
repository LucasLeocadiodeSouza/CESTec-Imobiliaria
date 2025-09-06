package com.cestec.cestec.service.cri;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.repository.corretorRepository;
import com.cestec.cestec.repository.cri.clienteRepository;
import com.cestec.cestec.repository.cri.contratoRepository;
import com.cestec.cestec.repository.cri.imovelRepository;
import com.cestec.cestec.repository.cri.proprietarioRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.util.utilForm;

@Service
public class pcri001s {

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

    @Autowired
    private genService gen;

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
            utilForm.criarColuna(gen.getNomeByCodFunc(contratos.get(i).getCodcorretor()));
        }

        return utilForm.criarGrid();
    }
}
