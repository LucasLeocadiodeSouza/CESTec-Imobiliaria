package com.cestec.cestec.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.model.pcp_contrato;
import com.cestec.cestec.repository.contratoRepository;
import com.cestec.cestec.repository.userRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.util.utilForm;

@Service
public class wcr006s {
    
    @Autowired
    private contratoRepository contratoRepository;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private prjContratosCustomRepository contratosCustomRepository;
    
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByLogin(username);
    }

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

    public List<?> buscarContratoAprovacao(Integer codprop, Integer codcliente, Integer codcorretor, Integer acao){
        List<contratoDTO> contratos = contratosCustomRepository.buscarContratoAprovacao(codprop, codcliente, codcorretor, acao);

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
            utilForm.criarColuna(String.valueOf(contratos.get(i).getVlrcondominio()));
            utilForm.criarColuna(String.valueOf(contratos.get(i).getArea()));
            utilForm.criarColuna(contratos.get(i).getQuartos().toString());
            utilForm.criarColuna(contratos.get(i).getDocumento());
            utilForm.criarColuna(contratos.get(i).getEndereco());
            utilForm.criarColuna(String.valueOf(contratos.get(i).getValorliberado()));
            utilForm.criarColuna(contratos.get(i).getObservacao());
        }

        return utilForm.criarGrid();
    }

    @Transactional
    public ResponseEntity<?> aprovarReprovarContrato(pcp_contrato contratoDTO){
        try{
            if(contratoDTO.getSituacao() != 2 && contratoDTO.getSituacao() != 3) return ResponseEntity.badRequest().body("Acão inserida inválida! (2 - Aprovado | 3 - Reprovado)");

            if(loadUserByUsername(contratoDTO.getIdeusu()) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            pcp_contrato contratoAnalise = contratoRepository.findById(contratoDTO.getCodcontrato()).orElseThrow(() -> new RuntimeException("Erro: Não encontrado contrato com o código informado [" + contratoDTO.getCodcontrato() + "]"));

            if(contratoAnalise.getSituacao() != 1 && contratoAnalise.getSituacao() != 3) return ResponseEntity.badRequest().body("Contrato não está em situacão de se aprovado/reprovado!");

            contratoAnalise.setSituacao(contratoDTO.getSituacao());
            contratoAnalise.setObservacao(contratoDTO.getObservacao());
            contratoAnalise.setValorliberado(contratoDTO.getValorliberado());

            contratoRepository.save(contratoAnalise);

            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar o contrato: " + e.getMessage());    
        }
    }

}
