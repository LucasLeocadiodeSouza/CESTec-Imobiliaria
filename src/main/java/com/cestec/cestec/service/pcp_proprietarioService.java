package com.cestec.cestec.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.pcp_imovel;
import com.cestec.cestec.model.pcp_proprietario;
import com.cestec.cestec.repository.imovelRepository;
import com.cestec.cestec.repository.prjContratosCustomRepository;
import com.cestec.cestec.repository.proprietarioRepository;
import com.cestec.cestec.util.utilForm;

@Service
public class pcp_proprietarioService {

    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private imovelRepository imovelRepository;

     @Autowired
    private prjContratosCustomRepository contratosCustomRepository;

    public String validaProprietario(pcp_proprietario proprietario) {
        if (proprietario.getDocumento() == "") {
            return "Proprietario Não pode ser cadastrado sem um CPF";
        }
        if (proprietario.getEmail() == "") {
            return "Deve ser Preenchido o Campo Email do proprietario";
        }
        if (proprietario.getNome() == "") {
            return "Deve ser Preenchido o Campo Nome do proprietario";
        }
        if (proprietario.getNumtel() == "") {
            return "Deve ser informado o Numero de telefone do proprietario";
        }
        if (proprietario.getEndereco_bairro() == "") {
            return "Deve ser informado o Endereco [Bairro] do proprietario";
        }
        if (proprietario.getEndereco_numero() == "") {
            return "Deve ser informado o Endereco [Número] do proprietario";
        }
        if (proprietario.getEndereco_logradouro() == "") {
            return "Deve ser informado o Endereco [Logradouro] do proprietario";
        }
        if (proprietario.getEndereco_cep() == "") {
            return "Deve ser informado o Endereco [Cep] do proprietario";
        }
        if (proprietario.getEndereco_cep().length() != 9) {
            return "Cep [" + proprietario.getEndereco_cep().toString() + "] Informado inválido";
        }
        if (proprietario.getEndereco_cidade() == "") {
            return "Deve ser informado o Endereco [Cidade] do proprietario";
        }
        if (proprietario.getEndereco_uf() == "") {
            return "Deve ser informado o Endereco [UF] do proprietario";
        }
        return "OK";
    }

    @Transactional
    public ResponseEntity<String> salvarProprietario(pcp_proprietario pcp_proprietario) {
        String validacao = validaProprietario(pcp_proprietario);
        if (!validacao.equals("OK")) {
            return ResponseEntity.ok(validacao);
        }

        try {
            pcp_proprietario proprietarioAnalise = proprietarioRepository
                    .findByCodproprietario(pcp_proprietario.getCodproprietario());

            proprietarioAnalise.setDocumento(pcp_proprietario.getDocumento());
            proprietarioAnalise.setEmail(pcp_proprietario.getEmail());
            proprietarioAnalise.setEndereco_bairro(pcp_proprietario.getEndereco_bairro());
            proprietarioAnalise.setEndereco_cep(pcp_proprietario.getEndereco_cep());
            proprietarioAnalise.setEndereco_cidade(pcp_proprietario.getEndereco_cidade());
            proprietarioAnalise.setEndereco_complemento(pcp_proprietario.getEndereco_complemento());
            proprietarioAnalise.setEndereco_numero(pcp_proprietario.getEndereco_numero());
            proprietarioAnalise.setEndereco_uf(pcp_proprietario.getEndereco_uf());
            proprietarioAnalise.setEndereco_logradouro(pcp_proprietario.getEndereco_logradouro());
            proprietarioAnalise.setNome(pcp_proprietario.getNome());
            proprietarioAnalise.setNumtel(pcp_proprietario.getNumtel());
            proprietarioAnalise.setCriado_em(proprietarioAnalise.getCriado_em() == null ? LocalDateTime.now() : proprietarioAnalise.getCriado_em());
            proprietarioAnalise.setAtualizado_em(LocalDateTime.now());
            if (proprietarioAnalise.getCodproprietario() == null)
                proprietarioAnalise.setId_usuario(pcp_proprietario.getId_usuario());

            proprietarioRepository.save(proprietarioAnalise);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

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
        }
        ;
        return imovelRepository.save(imovel);
    }

    public List<pcp_proprietario> listarProprietarios() {
        return proprietarioRepository.findAll();
    }

    public List<pcp_imovel> listarImoveisPorProprietario(Integer codProprietario) {
        return imovelRepository.findByProprietario(codProprietario);
    }

    public List<pcp_imovel> listarImoveis() {
        return imovelRepository.findAll();
    }

    public List<?> buscarProprietario(Integer codprop) {
        List<pcp_proprietario> proprietarios = contratosCustomRepository.buscarProprietario(codprop);

        utilForm.initGrid();
        for (int i = 0; i < proprietarios.size(); i++) {
            String enderecocompl = proprietarios.get(i).getEndereco_bairro() + ", "
                    + proprietarios.get(i).getEndereco_numero() + ", " + proprietarios.get(i).getEndereco_cidade()
                    + ", " + proprietarios.get(i).getEndereco_uf();

            utilForm.criarRow();
            utilForm.criarColuna(proprietarios.get(i).getCodproprietario().toString());
            utilForm.criarColuna(proprietarios.get(i).getNome());
            utilForm.criarColuna(proprietarios.get(i).getDocumento().toString());
            utilForm.criarColuna(enderecocompl);
            utilForm.criarColuna(proprietarios.get(i).getNumtel().toString());
            utilForm.criarColuna(proprietarios.get(i).getEmail());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_logradouro());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_numero());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_cidade());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_uf());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_cep());
        }

        return utilForm.criarGrid();
    }

    public ImovelProprietarioDTO buscarImovelGrid(Integer index) {
        return imovelRepository.buscarImovelGrid(imovelRepository.buscarimoveis().get(index).getCodimovel(),
                imovelRepository.buscarimoveis().get(index).getCodproprietario());
    }

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

    public String getNomeProp(Integer codProprietario) {
        pcp_proprietario proprietario = proprietarioRepository.findById(codProprietario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, // Status HTTP 404
                        "Código do proprietario [" + codProprietario + "] não encontrado"));

        return proprietario.getNome();
    }

    public List<?> buscarImoveis() {
        List<ImovelProprietarioDTO> imoveis = imovelRepository.buscarimoveis();

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

    public String getEnderecoImovel(Integer codImovel) {
        pcp_imovel imovel = imovelRepository.findById(codImovel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não encontrado imovel para o código informado [" + codImovel + "]"));

        return imovel.getEndereco();
    }

    public String getTipoContratoImovel(Integer codImovel) {
        Integer negociacao = imovelRepository.findById(codImovel).get().getNegociacao();
        switch (negociacao) {
            case 1:
                return "Aluguel";
            case 2:
                return "Venda";
        }
        return "Tipo contrato não encontrado";
    }

    public double getValorImovel(Integer codImovel) {
        return imovelRepository.findById(codImovel).get().getPreco();
    }
}
