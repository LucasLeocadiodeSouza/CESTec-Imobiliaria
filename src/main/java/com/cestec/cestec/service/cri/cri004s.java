package com.cestec.cestec.service.cri;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.cri.contratoDTO;
import com.cestec.cestec.model.cri.pcp_cliente;
import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.model.cri.pcp_contratoId;
import com.cestec.cestec.model.cri.pcp_corretor;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.model.cri.pcp_proprietario;
import com.cestec.cestec.repository.corretorRepository;
import com.cestec.cestec.repository.cri.clienteRepository;
import com.cestec.cestec.repository.cri.contratoRepository;
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
    private sp_userService sp_user;

    @Autowired genService gen;

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

    public String getSituacaoContrato(Integer codsit) {
        switch (codsit) {
            case 0: return "Cancelado";
            case 1: return "Aberta";
            case 2: return "Aguardando aprovação";
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

    public Double getValorImovel(Integer codimovel) {
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

    public List<modelUtilForm> getOptionsImovel(Integer codpropr){
        List<modelUtilForm> listaOpt = new java.util.ArrayList<>();
        int i = 0;

        List<pcp_imovel> imoveis = new java.util.ArrayList<>();
        if(codpropr == null || codpropr == 0) imoveis = imovelRepository.findAtivos();
        else imoveis = imovelRepository.findAtivosByProprietario(codpropr);

        listaOpt.add(new modelUtilForm(0, "Selecione um Imovel"));

        if(imoveis == null) return listaOpt;

        do{
            String text = imoveis.get(i).getCodimovel().toString() + " - " + getTipoImovel(imoveis.get(i).getTipo()) + " - " + imoveis.get(i).getEndereco();

            listaOpt.add(new modelUtilForm(imoveis.get(i).getCodimovel(), text));
            i++;
        }while (i < imoveis.size());
        
        return listaOpt;
    }


    /******** Salvar *********/
    @Transactional
    public void salvarContrato(Integer codcontrato, Integer codcliente, Integer codprop, Integer codimovel, Double vlrnegoc, String ideusucorretor, Date datini, Date datfim, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        if(codcliente == null || codcliente == 0) throw new RuntimeException("É preciso informar o codigo do cliente para vincular com contrato!");

        if(codprop == null || codprop == 0) throw new RuntimeException("É preciso informar o codigo do proprietario para vincular com contrato!");

        if(codimovel == null || codimovel == 0) throw new RuntimeException("É preciso informar o codigo do imovel para vincular com contrato!");

        if(vlrnegoc == null || vlrnegoc == 0) throw new RuntimeException("É preciso informar um valor negociado do imovel para vincular com contrato!");

        if(vlrnegoc == null || vlrnegoc == 0) throw new RuntimeException("É preciso informar um valor negociado do imovel para vincular com contrato!");

        if(sp_user.loadUserByUsername(ideusucorretor) == null) throw new RuntimeException("Corretor não encontrado no sistema!");

        if(datini == null) throw new RuntimeException("É preciso informar uma data inicial do contrato do imovel!");
        if(datfim == null) throw new RuntimeException("É preciso informar uma data final do contrato do imovel!");

        pcp_imovel imovel = imovelRepository.findByCodimovel(codimovel);
        if(imovel == null) throw new RuntimeException("Imovel não encontrado com o codigo informado '" + codimovel + "'!");

        pcp_cliente cliente = clienteRepository.findByCodcliente(codcliente);
        if(cliente == null) throw new RuntimeException("Cliente não encontrado com o codigo informado '" + codcliente + "'!");

        pcp_proprietario proprietario = proprietarioRepository.findByCodproprietario(codprop);
        if(proprietario == null) throw new RuntimeException("Proprietario não encontrado com o codigo informado '" + codprop + "'!");

        pcp_corretor corretor = corretorRepository.findCorretorByIdeusu(ideusucorretor);
        if(corretor == null) throw new RuntimeException("Corretor não encontrado com o ideusu informado '" + ideusucorretor + "'!");

        pcp_contrato contratoAnalise = contratoRepository.findByCodContratoImovel(codcontrato, codimovel);

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
            pcp_contrato ultimoContrato = contratoRepository.findTopByOrderByIdDesc();
            
            Integer ultimoCodContrato = 1;
            if(ultimoContrato != null) ultimoCodContrato = ultimoContrato.getId().getCodcontrato();

            contratoAnalise.setId(new pcp_contratoId(ultimoCodContrato + 1, imovel.getCodimovel())); // ultimo registro do contrato no banco e codigo imovel do parametro

            contratoAnalise.setDatiregistro(LocalDate.now());
            contratoAnalise.setIdeusu(ideusu);
            contratoAnalise.setSituacao(1);
            contratoAnalise.setAtivo(true);
        }

        contratoRepository.save(contratoAnalise);
   }

   @Transactional
    public void cancelarContrato(Integer codcontrato, Integer codimovel, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        if(codimovel == null || codimovel == 0) throw new RuntimeException("É preciso informar o codigo do imovel para vincular com contrato!");

        pcp_imovel imovel = imovelRepository.findByCodimovel(codimovel);
        if(imovel == null) throw new RuntimeException("Imovel não encontrado com o codigo informado '" + codimovel + "'!");

        pcp_contrato contratoAnalise = contratoRepository.findByCodContratoImovel(codcontrato, codimovel);

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

        pcp_contrato contratoAnalise = contratoRepository.findByCodContratoImovel(codcontrato, codimovel);

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
            utilForm.criarRow();
            utilForm.criarColuna(contratos.get(i).getCodcontrato().toString());
            utilForm.criarColuna(getSituacaoContrato(contratos.get(i).getSituacao()));
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
            utilForm.criarColuna(gen.getIdeusuByCodFunc(contratos.get(i).getCodcorretor()));
            utilForm.criarColuna(contratos.get(i).getTipo().toString());
            utilForm.criarColuna(gen.getNomeByCodFunc(contratos.get(i).getCodcorretor()));
            utilForm.criarColuna(contratos.get(i).getSituacao().toString());
        }

        return utilForm.criarGrid();
    }
}
