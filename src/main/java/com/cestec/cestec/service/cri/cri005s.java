package com.cestec.cestec.service.cri;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.imagemDTO;
import com.cestec.cestec.model.cri.contratoDTO;
import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.model.cri.pcp_imovel_img;
import com.cestec.cestec.repository.cri.contratoRepository;
import com.cestec.cestec.repository.cri.imovelImgRepo;
import com.cestec.cestec.repository.cri.imovelRepository;
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
    private imovelRepository imovelRepository;

    @Autowired
    private imovelImgRepo imovelImgRepo;

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

    public String getSituacaoContrato(Integer codsit) {
        switch (codsit) {
            case 0: return "<div title='Cancelada' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#ff0000;'></div></div></div>";
            case 1: return "<div title='Aberta' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#5a7cd0;'></div></div></div>";
            case 2: return "<div title='Aguardando aprovação' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#ffeb00;'></div></div></div>";
            case 3: return "<div title='Aprovada' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#035e00;'></div></div></div>";
            case 4: return "<div title='Reprovada' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#ff8100;'></div></div></div>";
        }
        return "";
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
            utilForm.criarColuna(getSituacaoContrato(contratos.get(i).getSituacao()));
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

        public List<imagemDTO> buscarImagensImovel(Integer codimovel){
        List<pcp_imovel_img> imagens = imovelImgRepo.findAllImgByCodimovel(codimovel);

        List<imagemDTO> pathSrcs = new ArrayList<>();

        for (pcp_imovel_img pcp_imovel_img : imagens) {
            pathSrcs.add(new imagemDTO(pcp_imovel_img.getId().getSeq(), pcp_imovel_img.getImgpath()));
        }

        return pathSrcs;
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

        if(codsituacao == 3){
            pcp_imovel imovel = imovelRepository.findByCodimovel(contrato.getPcp_imovel().getCodimovel());
            if(imovel.getStatus() != 1) throw new RuntimeException("O imovel está com status de '" + getDescStatus(imovel.getStatus()) + "' e não pode ser aprovado para o contrato!");

            if(imovel.getPreco().compareTo(valorliberado) < 0) throw new RuntimeException("O valor liberado do imovel não pode ser maior que o valor do preco do imovel!");

            imovel.setStatus(2);
        }

        contrato.setSituacao(codsituacao);
        contrato.setObservacao(observacao);
        contrato.setValorliberado(valorliberado);

        contratoRepository.save(contrato);
    }

}
