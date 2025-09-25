package com.cestec.cestec.service.cri;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.controller.cri.cri001c;
import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.imagemDTO;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.model.cri.pcp_imovel_img;
import com.cestec.cestec.model.cri.pcp_imovel_imgId;
import com.cestec.cestec.model.cri.pcp_proprietario;
import com.cestec.cestec.repository.cri.imovelImgRepo;
import com.cestec.cestec.repository.cri.imovelRepository;
import com.cestec.cestec.repository.cri.proprietarioRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;
import jakarta.transaction.Transactional;

@Service
public class cri001s {
    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private imovelRepository imovelRepository;

    @Autowired
    private imovelImgRepo imovelImgRepo;

    @Autowired
    private prjContratosCustomRepository contratosCustomRepo;

    @Autowired
    private sp_userService sp_user;

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


    /* ********* Options ******** */ 
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

    public List<imagemDTO> buscarImagensImovel(Integer codimovel){
        List<pcp_imovel_img> imagens = imovelImgRepo.findAllImgByCodimovel(codimovel);

        List<imagemDTO> pathSrcs = new ArrayList<>();

        for (pcp_imovel_img pcp_imovel_img : imagens) {
            pathSrcs.add(new imagemDTO(pcp_imovel_img.getId().getSeq(), pcp_imovel_img.getImgpath()));
        }

        return pathSrcs;
    }

    /* ******** Salvar ********* */ 
    @Transactional
    public void salvarImovel(pcp_imovel imovel, Integer codproprietario, String ideusu) {
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        if(imovel.getNegociacao() == null || getDescTipos(imovel.getNegociacao()) == "") throw new RuntimeException("O tipo de contrato informado '" + getDescTipos(imovel.getNegociacao()) +"' é invalido!");
        if(imovel.getTipo() == null || getTipoImovel(imovel.getTipo()) == "") throw new RuntimeException("O tipo de imovel informado '" + getTipoImovel(imovel.getTipo()) +"' é invalido!");
        if(imovel.getArea() == null || imovel.getArea() == 0.00) throw new RuntimeException("É preciso informar o campo 'Area Total(M²)' para salvar o registro do Imovel!");
        if(imovel.getQuartos() == null || imovel.getQuartos() == 0) throw new RuntimeException("É preciso informar o campo 'Quartos' para salvar o registro do Imovel!");
        if(imovel.getBanheiros() == null || imovel.getBanheiros() == 0) throw new RuntimeException("É preciso informar o campo 'Banheiros' para salvar o registro do Imovel!");
        if(imovel.getEndereco_bairro() == null || imovel.getEndereco_bairro() == "") throw new RuntimeException("É preciso informar o campo 'Bairro' para salvar o registro do Imovel!");
        if(imovel.getEndereco_rua() == null    || imovel.getEndereco_rua() == "")    throw new RuntimeException("É preciso informar o campo 'Rua' para salvar o registro do Imovel!");
        if(imovel.getEndereco_cidade() == null || imovel.getEndereco_cidade() == "") throw new RuntimeException("É preciso informar o campo 'Cidade' para salvar o registro do Imovel!");
        if(imovel.getEndereco_estado() == null || imovel.getEndereco_estado() == "") throw new RuntimeException("É preciso informar o campo 'Estado' para salvar o registro do Imovel!");
        if(imovel.getEndereco_postal() == null || imovel.getEndereco_postal() == "") throw new RuntimeException("É preciso informar o campo 'CEP' para salvar o registro do Imovel!");
        if(imovel.getEndereco_numero() == null || imovel.getEndereco_numero() == "") throw new RuntimeException("É preciso informar o campo 'Número' para salvar o registro do Imovel!");
        if(imovel.getPreco() == null || imovel.getPreco().compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("É preciso informar o campo 'Valor (R$)' para salvar o registro do Imovel!");
        if(imovel.getDatinicontrato() == null) throw new RuntimeException("É preciso informar o campo 'Inicio do Contrato' para salvar o registro do Imovel!");

        pcp_imovel imovelAnalise = imovelRepository.findByCodimovel(imovel.getCodimovel());
        if(imovelAnalise == null) imovelAnalise = new pcp_imovel();

        if(imovelAnalise.getStatus() != null && imovelAnalise.getStatus() == 3) throw new RuntimeException("Não é possível alterar as informacoes do imovel Inativo!");

        imovelAnalise.setArea(imovel.getArea());
        imovelAnalise.setEndereco_bairro(imovel.getEndereco_bairro());
        imovelAnalise.setEndereco_rua(imovel.getEndereco_rua());
        imovelAnalise.setEndereco_cidade(imovel.getEndereco_cidade());
        imovelAnalise.setEndereco_estado(imovel.getEndereco_estado());
        imovelAnalise.setEndereco_postal(imovel.getEndereco_postal());
        imovelAnalise.setEndereco_numero(imovel.getEndereco_numero());
        imovelAnalise.setNegociacao(imovel.getNegociacao());
        imovelAnalise.setPreco(imovel.getPreco());
        imovelAnalise.setQuartos(imovel.getQuartos());
        imovelAnalise.setBanheiros(imovel.getBanheiros());
        imovelAnalise.setTipo(imovel.getTipo());
        imovelAnalise.setVlrcondominio(imovel.getVlrcondominio());

        if(imovel.getCodimovel() == null){
            pcp_proprietario proprietario = proprietarioRepository.findById(codproprietario).orElseThrow(() -> new RuntimeException("Proprietário não encontrado com o código: " + codproprietario));
            imovelAnalise.setPcp_proprietario(proprietario);
            imovelAnalise.setDatinicontrato(imovel.getDatinicontrato());
            imovelAnalise.setStatus(1);
            imovelAnalise.setIdeusu(ideusu);
            imovelAnalise.setDatiregistro(LocalDate.now());
        }

        imovelRepository.save(imovelAnalise);
    }

     @Transactional
    public void inativarImovel(Integer codimovel, String ideusu) {
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        pcp_imovel imovelAnalise = imovelRepository.findByCodimovel(codimovel);
        if(imovelAnalise == null) throw new RuntimeException("O codigo do imovel informado '" + codimovel + "' é invalido!");

        if(imovelAnalise.getStatus() != 1) throw new RuntimeException("O Imovel informado '" + codimovel + "' não pode ser desativado pois está com o estado de '" + getDescStatus(imovelAnalise.getStatus()) + "'!");
        
        imovelAnalise.setStatus(3);

        imovelRepository.save(imovelAnalise);
    }

    @Transactional
    public void adicionarImagemImovel(String image, Integer codimovel, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        pcp_imovel imovelAnalise = imovelRepository.findByCodimovel(codimovel);
        if(imovelAnalise == null) throw new RuntimeException("O codigo do imovel informado '" + codimovel + "' é invalido!");

        Integer seqimg = imovelImgRepo.findMaxSeqByCodimovel(codimovel);
        if(seqimg == null) seqimg = 0;

        pcp_imovel_img imagemimovel = new pcp_imovel_img();
        imagemimovel.setCriado_em(LocalDate.now());
        imagemimovel.setIdeusu(ideusu);
        imagemimovel.setPcp_imovel(imovelAnalise);
        imagemimovel.setImgpath(image);
        imagemimovel.setId(new pcp_imovel_imgId(seqimg + 1, codimovel));

        imovelImgRepo.save(imagemimovel);
    }

    @Transactional
    public void removerImagemImovel(Integer seq, Integer codimovel, String ideusu) throws IOException{
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        pcp_imovel imovelAnalise = imovelRepository.findByCodimovel(codimovel);
        if(imovelAnalise == null) throw new RuntimeException("O codigo do imovel informado '" + codimovel + "' é invalido!");

        pcp_imovel_img imovelImg = imovelImgRepo.findImgById(seq, codimovel);
        if(imovelImg == null) throw new RuntimeException("Não encontrado uma imagem referente ao imovel de código '" + codimovel + "' com a sequencia informada '" + seq + "'!");

        Path imagePath = Paths.get(cri001c.uploadDirectory, imovelImg.getImgpath());
        Files.deleteIfExists(imagePath);

        imovelImgRepo.delete(imovelImg);
    }

    /* ***************** */ 

    /* ********* GRIDS ******** */ 
    public List<?> buscarImoveis(Integer codcontrato, Integer codprop, Integer tipimovel) {
        List<ImovelProprietarioDTO> imoveis = contratosCustomRepo.buscarImoveisGrid(codcontrato, codprop, tipimovel);

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
            utilForm.criarColuna(imoveis.get(i).getEndereco_bairro());
            utilForm.criarColuna(imoveis.get(i).getEndereco_numero().toString());
            utilForm.criarColuna(imoveis.get(i).getEndereco_postal().toString());
            utilForm.criarColuna(imoveis.get(i).getEndereco_cidade());
            utilForm.criarColuna(imoveis.get(i).getEndereco_estado());
            utilForm.criarColuna(imoveis.get(i).getEndereco_rua());
            utilForm.criarColuna(imoveis.get(i).getArea().toString());
            utilForm.criarColuna(imoveis.get(i).getQuartos().toString());
            utilForm.criarColuna(String.valueOf(imoveis.get(i).getVlrcondominio()));
            utilForm.criarColuna(imoveis.get(i).getDatinicontrato().toString());
            utilForm.criarColuna(imoveis.get(i).getTipo().toString());
            utilForm.criarColuna(imoveis.get(i).getNegociacao().toString());
            utilForm.criarColuna(imoveis.get(i).getStatus().toString());
            utilForm.criarColuna(imoveis.get(i).getBanheiros().toString());
        }

        return utilForm.criarGrid();
    }
}
