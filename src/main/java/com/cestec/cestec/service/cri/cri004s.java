package com.cestec.cestec.service.cri;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.imagemDTO;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.cri.contratoDTO;
import com.cestec.cestec.model.cri.pcp_cliente;
import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.model.cri.pcp_corretor;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.model.cri.pcp_imovel_img;
import com.cestec.cestec.model.cri.pcp_proprietario;
import com.cestec.cestec.repository.corretorRepository;
import com.cestec.cestec.repository.cri.clienteRepository;
import com.cestec.cestec.repository.cri.contratoRepository;
import com.cestec.cestec.repository.cri.imovelImgRepo;
import com.cestec.cestec.repository.cri.imovelRepository;
import com.cestec.cestec.repository.cri.proprietarioRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

import jakarta.transaction.Transactional;

@Service
public class cri004s {
    
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
    private imovelImgRepo imovelImgRepo;

    @Autowired
    private sp_userService sp_user;

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

    public String getSituacaoContratoColor(Integer codsit) {
        switch (codsit) {
            case 0: return "<div title='Cancelada' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#ff0000;'></div></div></div>";
            case 1: return "<div title='Aberta' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#5a7cd0;'></div></div></div>";
            case 2: return "<div title='Aguardando aprovação' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#ffeb00;'></div></div></div>";
            case 3: return "<div title='Aprovada' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#035e00;'></div></div></div>";
            case 4: return "<div title='Reprovada' class='gridlegenda'><div class='lgde'><div class='lgdi' style='background:#ff8100;'></div></div></div>";
        }
        return "";
    }

    public String getSituacaoContrato(Integer codsit) {
        switch (codsit) {
            case 0: return "Cancelada";
            case 1: return "Aberta";
            case 2: return "Aguardando";
            case 3: return "Aprovada";
            case 4: return "Reprovada";
        }
        return "";
    }

    public String getBuscaTipoImovel(Integer codimovel) {
        pcp_imovel imovel = imovelRepository.findByCodimovel(codimovel);
        if(imovel == null)  throw new RuntimeException("Código do Imovel [" + codimovel + "] não encontrado!");

        return getTipoImovel(imovel.getTipo());
    }

    public BigDecimal getValorImovel(Integer codimovel) {
        pcp_imovel imovel = imovelRepository.findByCodimovel(codimovel);
        if(imovel == null)  throw new RuntimeException("Código do Imovel [" + codimovel + "] não encontrado!");

        return imovel.getPreco();
    }

    public String getTipoContratoImovel(Integer codimovel) {
        pcp_imovel imovel = imovelRepository.findByCodimovel(codimovel);
        if(imovel == null)  throw new RuntimeException("Código do Imovel [" + codimovel + "] não encontrado!");

        return getDescTipos(imovel.getNegociacao());
    }

    public Integer getProprietarioByImovel(Integer codimovel) {
        pcp_imovel imovel = imovelRepository.findByCodimovel(codimovel);
        if(imovel == null)  throw new RuntimeException("Código do Imovel [" + codimovel + "] não encontrado!");

        return imovel.getPcp_proprietario().getCodproprietario();
    }

    public List<modelUtilForm> getOptionsImovel(Integer codpropr, Boolean somenteativos){
        List<modelUtilForm> listaOpt = new java.util.ArrayList<>();
        int i = 0;

        List<pcp_imovel> imoveis = new java.util.ArrayList<>();
        if(codpropr == null || codpropr == 0) {
            if(somenteativos) imoveis = imovelRepository.findAtivos(); 
            else imoveis = imovelRepository.findAll();
        }
        else {
            if(somenteativos) imoveis = imovelRepository.findAtivosByProprietario(codpropr);
            else imoveis = imovelRepository.findByProprietario(codpropr);
        }

        listaOpt.add(new modelUtilForm(0, "Selecione um Imovel"));

        if(imoveis == null) return listaOpt;

        do{
            String text = imoveis.get(i).getCodimovel().toString() + " - " + getTipoImovel(imoveis.get(i).getTipo()) + " - " + getDescTipos(imoveis.get(i).getNegociacao());

            listaOpt.add(new modelUtilForm(imoveis.get(i).getCodimovel(), text));
            i++;
        }while (i < imoveis.size());
        
        return listaOpt;
    }

    public List<imagemDTO> buscarImagensImovel(Integer codimovel){
        List<pcp_imovel_img> imagens = imovelImgRepo.findAllImgByCodimovel(codimovel);

        List<imagemDTO> pathSrcs = new ArrayList<>();

        for (pcp_imovel_img pcp_imovel_img : imagens) {
            pathSrcs.add(new imagemDTO(pcp_imovel_img.getId().getSeq(), pcp_imovel_img.getImgpath()));
        }

        return pathSrcs;
    }

    /******** Salvar *********/
    @Transactional
    public void salvarContrato(Integer codcontrato, Integer codcliente, Integer codprop, Integer codimovel, BigDecimal vlrnegoc, String ideusucorretor, Date datini, Date datfim, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        if(codcliente == null || codcliente == 0) throw new RuntimeException("É preciso informar o codigo do cliente para vincular com contrato!");

        if(codprop == null || codprop == 0) throw new RuntimeException("É preciso informar o codigo do proprietario para vincular com contrato!");

        if(codimovel == null || codimovel == 0) throw new RuntimeException("É preciso informar o codigo do imovel para vincular com contrato!");

        if(vlrnegoc == null || vlrnegoc.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("É preciso informar um valor negociado do imovel para vincular com contrato!");

        if(sp_user.loadUserByUsername(ideusucorretor) == null) throw new RuntimeException("Corretor não encontrado no sistema!");

        if(datini == null) throw new RuntimeException("É preciso informar uma data inicial do contrato do imovel!");
        if(datfim == null) throw new RuntimeException("É preciso informar uma data final do contrato do imovel!");

        pcp_imovel imovel = imovelRepository.findByCodimovelAndCodprop(codimovel,codprop);
        if(imovel == null) throw new RuntimeException("Imovel não encontrado com o codigo informado '" + codimovel + "'!");

        pcp_cliente cliente = clienteRepository.findByCodcliente(codcliente);
        if(cliente == null) throw new RuntimeException("Cliente não encontrado com o codigo informado '" + codcliente + "'!");

        pcp_proprietario proprietario = proprietarioRepository.findByCodproprietario(codprop);
        if(proprietario == null) throw new RuntimeException("Proprietario não encontrado com o codigo informado '" + codprop + "'!");

        pcp_corretor corretor = corretorRepository.findCorretorByIdeusu(ideusucorretor);
        if(corretor == null) throw new RuntimeException("Corretor não encontrado com o ideusu informado '" + ideusucorretor + "'!");

        if(contratoRepository.findContratoAtivoByCodImovel(codimovel) != null) throw new RuntimeException("Já existe um contrato ativo para o imovel com código informado '" + codimovel + "'!");

        pcp_contrato contratoAnalise = contratoRepository.findByCodContrato(codcontrato);

        if(contratoAnalise == null) contratoAnalise = new pcp_contrato();
        else if(contratoAnalise.getSituacao() != 1) throw new RuntimeException("O contrato não esta em uma situacão que permita alteracão de informacões");
        
        //if(imovel.getPcp_proprietario() == cliente) throw new RuntimeException("O imovel selecionado para a abertura do contrato já é pertencente ao proprio proprietario!");
        
        contratoAnalise.setPcp_cliente(cliente);
        contratoAnalise.setPcp_imovel(imovel);
        contratoAnalise.setPcp_proprietario(proprietario);
        contratoAnalise.setPcp_corretor(corretor);
        contratoAnalise.setDatinicio(datini);
        contratoAnalise.setDatfinal(datfim);
        contratoAnalise.setValor(vlrnegoc);
        
        if(contratoAnalise.getId() == null){
            contratoAnalise.setDatiregistro(LocalDate.now());
            contratoAnalise.setIdeusu(ideusu);
            contratoAnalise.setSituacao(1);
            contratoAnalise.setAtivo(true);
            contratoAnalise.setValorliberado(BigDecimal.ZERO);
        }

        contratoRepository.save(contratoAnalise);
   }

   @Transactional
    public void cancelarContrato(Integer codcontrato, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        pcp_contrato contratoAnalise = contratoRepository.findByCodContrato(codcontrato);

        if(contratoAnalise == null) throw new RuntimeException("O contrato não foi encontrado com o codigo do contrato e imovel informado");
        else if(contratoAnalise.getSituacao() != 1) throw new RuntimeException("O contrato não esta em uma situacão que permita alteracão de informacões");

        contratoAnalise.setSituacao(0);
        contratoAnalise.setAtivo(false);

        contratoRepository.save(contratoAnalise);
   }

   @Transactional
    public void enviarContratoAprovacao(Integer codcontrato, Integer codimovel, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        if(codimovel == null || codimovel == 0) throw new RuntimeException("É preciso informar o codigo do imovel para vincular com contrato!");

        pcp_imovel imovel = imovelRepository.findByCodimovel(codimovel);
        if(imovel == null) throw new RuntimeException("Imovel não encontrado com o codigo informado '" + codimovel + "'!");

        pcp_contrato contratoAnalise = contratoRepository.findByCodContrato(codcontrato);

        if(contratoAnalise == null) throw new RuntimeException("O contrato não foi encontrado com o codigo do contrato e imovel informado");
        else if(contratoAnalise.getSituacao() != 1 && contratoAnalise.getSituacao() != 4) throw new RuntimeException("O contrato não esta em uma situacão que permita alteracão de informacões");

        contratoAnalise.setSituacao(2);

        contratoRepository.save(contratoAnalise);
   }
   /*****************/

    /*  ******* GRIDS ****** */
    public List<?> buscarContratoGrid(Integer codprop, Integer codcliente, Integer tipimovel){
        List<contratoDTO> contratos = contratosCustomRepository.buscarContratoGrid(codprop,codcliente,tipimovel);

        utilForm.initGrid();
        for (int i = 0; i < contratos.size(); i++) {
            pcp_contrato contrato = contratoRepository.findByCodContrato(contratos.get(i).getCodcontrato());
            
            String valorlib;
            if(contrato.getValorliberado() == null) valorlib = "0.00";
            else valorlib = contrato.getValorliberado().toString();


            utilForm.criarRow();
            utilForm.criarColuna(contratos.get(i).getCodcontrato().toString());
            utilForm.criarColuna(getSituacaoContratoColor(contratos.get(i).getSituacao()));
            utilForm.criarColuna(contratos.get(i).getCodcliente().toString());
            utilForm.criarColuna(contratos.get(i).getNomeCliente());
            utilForm.criarColuna(contratos.get(i).getCodproprietario().toString());
            utilForm.criarColuna(contratos.get(i).getNomeProp());
            utilForm.criarColuna(contratos.get(i).getCodimovel().toString());
            utilForm.criarColuna(getTipoImovel(contratos.get(i).getTipo()));
            utilForm.criarColuna(getDescTipos(contratos.get(i).getNegociacao()));
            utilForm.criarColuna(contratos.get(i).getPreco().toString());
            utilForm.criarColuna(contratos.get(i).getDatinicio().toString());
            utilForm.criarColuna(contratos.get(i).getDatfinal().toString());
            utilForm.criarColuna(contratos.get(i).getValor().toString());
            utilForm.criarColuna(contratos.get(i).getEndereco_bairro());
            utilForm.criarColuna(gen.getIdeusuCorretorById(contratos.get(i).getCodcorretor()));
            utilForm.criarColuna(contratos.get(i).getTipo().toString());
            utilForm.criarColuna(gen.getNomeCorretorById(contratos.get(i).getCodcorretor()));
            utilForm.criarColuna(contratos.get(i).getSituacao().toString());
            utilForm.criarColuna(getSituacaoContrato(contratos.get(i).getSituacao()));
            utilForm.criarColuna(valorlib);
        }

        return utilForm.criarGrid();
    }
}
