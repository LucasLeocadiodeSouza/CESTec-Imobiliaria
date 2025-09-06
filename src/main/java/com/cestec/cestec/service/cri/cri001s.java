package com.cestec.cestec.service.cri;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.model.cri.pcp_proprietario;
import com.cestec.cestec.repository.cri.contratosCustomRepo;
import com.cestec.cestec.repository.cri.imovelRepository;
import com.cestec.cestec.repository.cri.proprietarioRepository;
import com.cestec.cestec.util.utilForm;

@Service
public class cri001s {
    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private imovelRepository imovelRepository;

    @Autowired
    private contratosCustomRepo contratosCustomRepo;


    public String getBuscaTipoImovel(Integer codImovel) {
        Integer tipo = imovelRepository.findById(codImovel).get().getTipo();

        switch (tipo) {
            case 1:
                return "Apartamento";
            case 2:
                return "Casa";
            case 3:
                return "Terreno";
        }
        return "Tipo do imovel não encontrado";
    }

    public String getTipoImovel(Integer codtipo) {
        switch (codtipo) {
            case 1:
                return "Apartamento";
            case 2:
                return "Casa";
            case 3:
                return "Terreno";
        }
        return "";
    }

    public String getDescTipos(Integer tipo) {
        switch (tipo) {
            case 1:
                return "Aluguel";
            case 2:
                return "Venda";
        }
        return "";
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
        return "";
    }

    public String getEnderecoImovel(Integer codImovel) {
        pcp_imovel imovel = imovelRepository.findById(codImovel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não encontrado imovel para o código informado [" + codImovel + "]"));

        return imovel.getEndereco();
    }
    
    public double getValorImovel(Integer codImovel) {
        return imovelRepository.findById(codImovel).get().getPreco();
    }


    /* ********* Options ******** */ 
    public String getTipoContratoImovel(Integer codImovel) {
        Integer negociacao = imovelRepository.findById(codImovel).get().getNegociacao();
        return getDescTipos(negociacao);
    }

    public List<modelUtilForm> getOptionsTpContrato(){
        List<modelUtilForm> listaOpt = new java.util.ArrayList<>();
        int i = 1;

        listaOpt.add(new modelUtilForm(0, "Selecione o Tipo de Contrato"));

        do{
            listaOpt.add(new modelUtilForm(i, getDescTipos(i)));
            i++;
        }while (getDescTipos(i) != "");
        
        return listaOpt;
    }

    public List<modelUtilForm> getOptionsTpImovel(){
        List<modelUtilForm> listaOpt = new java.util.ArrayList<>();
        int i = 1;

        listaOpt.add(new modelUtilForm(0, "Selecione o Tipo de Imovel"));

        do{
            listaOpt.add(new modelUtilForm(i, getTipoImovel(i)));
            i++;
        }while (getTipoImovel(i) != "");
        
        return listaOpt;
    }

    /* ***************** */ 

    /* ******** Salvar ********* */ 
    public pcp_imovel salvarImovel(pcp_imovel imovel, Integer codproprietario) {
        pcp_proprietario proprietario = proprietarioRepository.findById(codproprietario)
                .orElseThrow(
                        () -> new RuntimeException("Proprietário não encontrado com o código: " + codproprietario));
        imovel.setPcp_proprietario(proprietario);

        pcp_imovel verificaimovel = imovelRepository.existeimovel(imovel.getCodimovel(), codproprietario);
        if (verificaimovel != null) {
            verificaimovel.setArea(imovel.getArea());
            verificaimovel.setDatinicontrato(imovel.getDatinicontrato());
            verificaimovel.setEndereco(imovel.getEndereco());
            verificaimovel.setNegociacao(imovel.getNegociacao());
            verificaimovel.setPreco(imovel.getPreco());
            verificaimovel.setPcp_proprietario(proprietario);
            verificaimovel.setQuartos(imovel.getQuartos());
            verificaimovel.setStatus(imovel.getStatus());
            verificaimovel.setTipo(imovel.getTipo());
            verificaimovel.setVlrcondominio(imovel.getVlrcondominio());
        };

        return imovelRepository.save(imovel);
    }
    /* ***************** */ 

    /* ********* GRIDS ******** */ 
    public ImovelProprietarioDTO buscarImovelGrid(Integer index) {
        return imovelRepository.buscarImovelGrid(imovelRepository.buscarimoveis().get(index).getCodimovel(),
                imovelRepository.buscarimoveis().get(index).getCodproprietario());
    }

    public List<pcp_imovel> listarImoveis() {
        return imovelRepository.findAll();
    }

    public List<?> buscarImoveis(Integer codcontrato, Integer codprop, Integer tipimovel) {
        List<ImovelProprietarioDTO> imoveis = contratosCustomRepo.buscarContratoAprovacao(codcontrato, codprop, tipimovel);

        utilForm.initGrid();
        for (int i = 0; i < imoveis.size(); i++) {
            String desctipoimov = getTipoImovel(imoveis.get(i).getTipo());
            String descstatus = getDescStatus(imoveis.get(i).getStatus());
            String desctiponego = getDescTipos(imoveis.get(i).getNegociacao());

            utilForm.criarRow();
            utilForm.criarColuna(imoveis.get(i).getCodimovel().toString());
            utilForm.criarColuna(imoveis.get(i).getCodproprietario().toString());
            utilForm.criarColuna(imoveis.get(i).getNome());
            utilForm.criarColuna(desctipoimov);
            utilForm.criarColuna(descstatus);
            utilForm.criarColuna(String.valueOf(imoveis.get(i).getPreco()));
            utilForm.criarColuna(desctiponego);
            utilForm.criarColuna(imoveis.get(i).getEndereco());
            utilForm.criarColuna(imoveis.get(i).getArea().toString());
            utilForm.criarColuna(imoveis.get(i).getQuartos().toString());
            utilForm.criarColuna(String.valueOf(imoveis.get(i).getVlrcondominio()));
            utilForm.criarColuna(imoveis.get(i).getDatinicontrato().toString());
            utilForm.criarColuna(imoveis.get(i).getTipo().toString());
            utilForm.criarColuna(imoveis.get(i).getNegociacao().toString());
        }

        return utilForm.criarGrid();
    }
}
