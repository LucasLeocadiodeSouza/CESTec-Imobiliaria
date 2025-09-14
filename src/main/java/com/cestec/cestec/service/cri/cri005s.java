package com.cestec.cestec.service.cri;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.cri.contratoDTO;
import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.repository.cri.contratoRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

import jakarta.transaction.Transactional;

@Service
public class cri005s {
    
    @Autowired
    private contratoRepository contratoRepository;

    @Autowired
    private prjContratosCustomRepository contratosCustomRepository;

    @Autowired
    private sp_userService sp_user;

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

    private String getDescTipos(Integer tipo) {
        switch (tipo) {
            case 1:
                return "Aluguel";
            case 2:
                return "Venda";
        }
        return "Descricão não encontrada";
    }

    private String getDescStatus(Integer status) {
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

    public List<?> buscarContratoAprovacao(Integer codprop, Integer codcliente, Integer codcorretor, Integer tipimovel, Integer acao){
        List<contratoDTO> contratos = contratosCustomRepository.buscarContratoAprovacaoGrid(codprop, codcliente, codcorretor, tipimovel, acao);

        utilForm.initGrid();
        for (int i = 0; i < contratos.size(); i++) {
            String documento;
            if(contratos.get(i).getPessoa_fisica()) documento = utilForm.formatDocToCpf(contratos.get(i).getDocumento());
            else documento = utilForm.formatDocToCnpj(contratos.get(i).getDocumento());

            String endereco = contratos.get(i).getEndereco_logradouro() + ", " + 
                              contratos.get(i).getEndereco_numero() + " - " + 
                              contratos.get(i).getEndereco_bairro() + ", " + 
                              contratos.get(i).getEndereco_cidade() + " - " +
                              contratos.get(i).getEndereco_uf() + "," + 
                              contratos.get(i).getEndereco_postal();

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
            utilForm.criarColuna(utilForm.formatarDataBrasil(contratos.get(i).getDatinicio()) + " - " + utilForm.formatarDataBrasil(contratos.get(i).getDatfinal()));
            utilForm.criarColuna(utilForm.formatVlr(contratos.get(i).getPreco().doubleValue(), 2));
            utilForm.criarColuna(utilForm.formatVlr(contratos.get(i).getValor().doubleValue(), 2));
            utilForm.criarColuna(utilForm.formatVlr(contratos.get(i).getVlrcondominio().doubleValue(), 2));
            utilForm.criarColuna(String.valueOf(contratos.get(i).getArea()));
            utilForm.criarColuna(contratos.get(i).getQuartos().toString());
            utilForm.criarColuna(documento);
            utilForm.criarColuna(endereco);
            utilForm.criarColuna(utilForm.formatVlr(contratos.get(i).getValorliberado().doubleValue(), 2));
            utilForm.criarColuna(contratos.get(i).getObservacao());
            utilForm.criarColuna((contratos.get(i).getPessoa_fisica()?"Sim":"Não"));
            utilForm.criarColuna((contratos.get(i).getBanheiros().toString()));
        }

        return utilForm.criarGrid();
    }

    @Transactional
    public void aprovarReprovarContrato(Integer codcontrato, BigDecimal valorliberado, String observacao, String acao, String ideusu){
        Integer codsituacao;

        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");
        
        if(codcontrato == null || codcontrato == 0) throw new RuntimeException("É necessario informar o código do contrato!");

        if(!acao.equals("aprovar") && !acao.equals("reprovar")) throw new RuntimeException("Acão inserida '" + acao + "' inválida! (Aprovar | Reprovar)");
        codsituacao = acao.equals("aprovar")?3:4;

        if(acao.equals("aprovar") && (valorliberado == null || valorliberado.compareTo(BigDecimal.ZERO) <= 0)) throw new RuntimeException("Para aprovar o contrato é necessário informar o valor liberado");
        if(acao.equals("reprovar") && (observacao == null || observacao.isBlank())) throw new RuntimeException("Para reprovar o contrato é necessário informar uma observacão sobre o motivo da reprovacão");

        pcp_contrato contrato = contratoRepository.findByCodContrato(codcontrato);
        if(contrato == null) throw new RuntimeException("Contrato não encontrato com o código do contrato informado '" + codcontrato + "'");

        if(contrato.getSituacao() != 2 && contrato.getSituacao() != 4) throw new RuntimeException("Contrato não está em situacão de ser aprovado/reprovado!");

        contrato.setSituacao(codsituacao);
        contrato.setObservacao(observacao);
        contrato.setValorliberado(valorliberado);

        contratoRepository.save(contrato);
    }

}
