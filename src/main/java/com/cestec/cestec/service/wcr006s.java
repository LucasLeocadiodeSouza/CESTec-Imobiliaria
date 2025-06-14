package com.cestec.cestec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.repository.contratoRepository;
import com.cestec.cestec.util.utilForm;

@Service
public class wcr006s {
    
    @Autowired
    private contratoRepository contratoRepository;

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

    public String getDescStatus(Integer status) {
        switch (status) {
            case 1:
                return "Ativo";
            case 2:
                return "Ocupado";
            case 3:
                return "Inativo";
        }
        return "Descricão não encontrada";
    }

    public List<?> buscarContratoAprovacao(){
        List<contratoDTO> contratos = contratoRepository.buscarContratoAprovacao();

        utilForm.initGrid();
        for (int i = 0; i < contratos.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(contratos.get(i).getCodcontrato().toString());
            utilForm.criarColuna(contratos.get(i).getCodimovel().toString());
            utilForm.criarColuna(getTipoImovel(contratos.get(i).getTipo()));
            utilForm.criarColuna(contratos.get(i).getCodproprietario().toString());
            utilForm.criarColuna(contratos.get(i).getNomeProp());
            utilForm.criarColuna(contratos.get(i).getCodcliente().toString());
            utilForm.criarColuna(contratos.get(i).getNomeCliente());
            utilForm.criarColuna(contratos.get(i).getCodcorretor().toString());
            utilForm.criarColuna(contratos.get(i).getNomeCorretor());
            utilForm.criarColuna(contratos.get(i).getDatinicio() + " - " + contratos.get(i).getDatfinal());
            utilForm.criarColuna(String.valueOf(contratos.get(i).getPreco()));
            utilForm.criarColuna(String.valueOf(contratos.get(i).getValor()));
        }

        return utilForm.criarGrid();
    }

}
