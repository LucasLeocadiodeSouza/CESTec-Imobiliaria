package com.cestec.cestec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.pcp_imovel;
import com.cestec.cestec.model.pcp_proprietario;
import com.cestec.cestec.repository.imovelRepository;
import com.cestec.cestec.repository.proprietarioRepository;

@Service
public class pcp_proprietarioService {
    
    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private imovelRepository imovelRepository;

    public String validaProprietario(pcp_proprietario proprietario){
        if(proprietario.getCpf() == ""){
            return "Proprietario Não pode ser cadastrado sem um CPF";
        }
        if(proprietario.getEmail() == ""){
            return "Deve ser Preenchido o Campo Email do proprietario";
        }
        if(proprietario.getNome() == ""){
            return "Deve ser Preenchido o Campo Nome do proprietario";
        }
        if(proprietario.getNumtel() == ""){
            return "Deve ser informado o Numero de telefone do proprietario";
        }
        return "OK";
    }

    public ResponseEntity<?> salvarProprietario(pcp_proprietario pcp_proprietario) {
        String validacao = validaProprietario(pcp_proprietario);        
        if (!validacao.equals("OK")) {
            return ResponseEntity.badRequest().body(validacao);
        }

        pcp_proprietario proprietarioAnalise = proprietarioRepository.findByCodproprietario(pcp_proprietario.getCodproprietario());

        if(proprietarioAnalise != null){
            proprietarioAnalise.setCpf(pcp_proprietario.getCpf());
            proprietarioAnalise.setEmail(pcp_proprietario.getEmail());
            proprietarioAnalise.setEndereco(pcp_proprietario.getEndereco());
            proprietarioAnalise.setNome(pcp_proprietario.getNome());
            proprietarioAnalise.setNumtel(pcp_proprietario.getNumtel());
        };

        pcp_proprietario salvo = proprietarioRepository.save(pcp_proprietario);
        return ResponseEntity.ok(salvo);
    }

    public pcp_imovel salvarImovel(pcp_imovel imovel, Integer codproprietario) {
        pcp_proprietario proprietario = proprietarioRepository.findById(codproprietario)
        .orElseThrow(() -> new RuntimeException("Proprietário não encontrado com o código: " + codproprietario)); 
        imovel.setPcp_proprietario(proprietario);

        pcp_imovel verificaimovel = imovelRepository.existeimovel(imovel.getCodimovel(),codproprietario);
        if(verificaimovel != null){
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

    public List<pcp_proprietario> listarProprietarios() {
        return proprietarioRepository.findAll();
    }

    public List<pcp_imovel> listarImoveisPorProprietario(Integer codProprietario) {
        return imovelRepository.findByProprietario(codProprietario);
    }

    public List<pcp_imovel> listarImoveis(){
        return imovelRepository.findAll();
    }

    public pcp_proprietario buscarProprietario(Integer index){
        return proprietarioRepository.findAll().get(index);
    }

    public ImovelProprietarioDTO buscarImovelGrid(Integer index){
        return imovelRepository.buscarImovelGrid(imovelRepository.buscarimoveis().get(index).getCodimovel(), imovelRepository.buscarimoveis().get(index).getCodproprietario());
    }

    public String getTipoImovel(Integer codImovel){
        Integer tipo = imovelRepository.findById(codImovel).get().getTipo();
        
        switch (tipo) {
            case 1: return "Apartamento";
            case 2: return "Casa";
            case 3: return "Terreno";
        }
        return "Tipo do imovel não encontrado";
    }

    public String getDescTipos(Integer tipo){
        switch (tipo) {
            case 1:
                return "Aluguel";
            case 2:
                return "Venda";        
        }
        return "Descricão não encontrada";
    }

    public String getNomeProp(Integer codProprietario){
        return proprietarioRepository.findById(codProprietario).get().getNome();
    }

    public List<ImovelProprietarioDTO> buscarImoveis(){
       return imovelRepository.buscarimoveis();
    }

    public String getEnderecoImovel(Integer codImovel){
        return imovelRepository.findById(codImovel).get().getEndereco();
    }

    public String getTipoContratoImovel(Integer codImovel){
        Integer negociacao = imovelRepository.findById(codImovel).get().getNegociacao();
        switch (negociacao) {
            case 1: return "Aluguel";
            case 2: return "Venda";
        }
        return "Tipo contrato não encontrado";
    }
    
    public double getValorImovel(Integer codImovel){
        return imovelRepository.findById(codImovel).get().getPreco();
    }
}
