package com.cestec.cestec.service.cri;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.model.cri.pcp_proprietario;
import com.cestec.cestec.repository.cri.imovelRepository;
import com.cestec.cestec.repository.cri.proprietarioRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class cri002s {

    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private imovelRepository imovelRepository;

    @Autowired
    private prjContratosCustomRepository contratosCustomRepository;

    @Autowired
    private sp_userService sp_user;

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
    public void salvarProprietario(pcp_proprietario pcp_proprietario) {
        if(sp_user.loadUserByUsername(pcp_proprietario.getIdeusu()) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        String validacao = validaProprietario(pcp_proprietario);
        if (!validacao.equals("OK"))  throw new RuntimeException(validacao);

        if(!pcp_proprietario.isPessoa_fisica() && pcp_proprietario.getDocumento().length() != 14) throw new RuntimeException("CPNJ inválido!");
        if(pcp_proprietario.isPessoa_fisica() && pcp_proprietario.getDocumento().length() != 11) throw new RuntimeException("CPF inválido!");

        pcp_proprietario proprietarioAnalise = proprietarioRepository.findByCodproprietario(pcp_proprietario.getCodproprietario());
        if(proprietarioAnalise == null) proprietarioAnalise = new pcp_proprietario();

        if(proprietarioAnalise.getCodproprietario() == null){
            proprietarioAnalise.setNome(pcp_proprietario.getNome());
            proprietarioAnalise.setDocumento(pcp_proprietario.getDocumento());
            proprietarioAnalise.setCriado_em(proprietarioAnalise.getCriado_em() == null ? LocalDateTime.now() : proprietarioAnalise.getCriado_em());
            proprietarioAnalise.setIdeusu(pcp_proprietario.getIdeusu());
            proprietarioAnalise.setPessoa_fisica(pcp_proprietario.isPessoa_fisica());
        }

        proprietarioAnalise.setEmail(pcp_proprietario.getEmail());
        proprietarioAnalise.setEndereco_bairro(pcp_proprietario.getEndereco_bairro());
        proprietarioAnalise.setEndereco_cep(pcp_proprietario.getEndereco_cep());
        proprietarioAnalise.setEndereco_cidade(pcp_proprietario.getEndereco_cidade());
        proprietarioAnalise.setEndereco_complemento(pcp_proprietario.getEndereco_complemento());
        proprietarioAnalise.setEndereco_numero(pcp_proprietario.getEndereco_numero());
        proprietarioAnalise.setEndereco_uf(pcp_proprietario.getEndereco_uf());
        proprietarioAnalise.setEndereco_logradouro(pcp_proprietario.getEndereco_logradouro());
        proprietarioAnalise.setNumtel(pcp_proprietario.getNumtel());
        proprietarioAnalise.setAtualizado_em(LocalDateTime.now());

        proprietarioRepository.save(proprietarioAnalise);
    }

    public List<pcp_proprietario> listarProprietarios() {
        return proprietarioRepository.findAll();
    }

    public List<pcp_imovel> listarImoveisPorProprietario(Integer codProprietario) {
        return imovelRepository.findByProprietario(codProprietario);
    }

    public List<?> buscarProprietario(Integer codprop) {
        List<pcp_proprietario> proprietarios = contratosCustomRepository.buscarProprietario(codprop);

        utilForm.initGrid();
        for (int i = 0; i < proprietarios.size(); i++) {
            String enderecocompl = proprietarios.get(i).getEndereco_bairro() + ", "
                    + proprietarios.get(i).getEndereco_numero() + ", " + proprietarios.get(i).getEndereco_cidade()
                    + ", " + proprietarios.get(i).getEndereco_uf();

            String documento = proprietarios.get(i).isPessoa_fisica()? utilForm.formatDocToCnpj(proprietarios.get(i).getDocumento()) : utilForm.formatDocToCpf(proprietarios.get(i).getDocumento());

            utilForm.criarRow();
            utilForm.criarColuna(proprietarios.get(i).getCodproprietario().toString());
            utilForm.criarColuna(proprietarios.get(i).getNome());
            utilForm.criarColuna(documento);
            utilForm.criarColuna(enderecocompl);
            utilForm.criarColuna(proprietarios.get(i).getNumtel().toString());
            utilForm.criarColuna(proprietarios.get(i).getEmail());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_bairro());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_logradouro());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_numero());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_cidade());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_uf());
            utilForm.criarColuna(proprietarios.get(i).getEndereco_cep());
            utilForm.criarColuna(Boolean.toString(proprietarios.get(i).isPessoa_fisica()));
        }

        return utilForm.criarGrid();
    }
}
