package com.cestec.cestec.service.cri;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.cri.pcp_cliente;
import com.cestec.cestec.repository.cri.clienteRepository;
import com.cestec.cestec.repository.custom.prjContratosCustomRepository;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class cri003s {
    
    @Autowired
    private clienteRepository clienteRepository;

    @Autowired
    private prjContratosCustomRepository contratosCustomRepository;

    @Autowired
    private sp_userService sp_user;

    public String validaCliente(pcp_cliente cliente){
        if(cliente.getDocumento() == ""){
            return "Proprietario não pode ser cadastrado sem um CPF";
        }
        if(cliente.getEmail() == ""){
            return "Deve ser Preenchido o Campo Email do proprietario";
        }
        if(cliente.getNome() == ""){
            return "Deve ser Preenchido o Campo Nome do proprietario";
        }
        if(cliente.getNumtel() == ""){
            return "Deve ser informado o Numero de telefone do proprietario";
        }
        if(cliente.getEndereco_bairro() == ""){
            return "Deve ser informado o Endereco [Bairro] do proprietario";
        }
        if(cliente.getEndereco_numero() == ""){
            return "Deve ser informado o Endereco [Número] do proprietario";
        }
        if(cliente.getEndereco_logradouro() == ""){
            return "Deve ser informado o Endereco [Logradouro] do proprietario";
        }
        if(cliente.getEndereco_cep() == ""){
            return "Deve ser informado o Endereco [Cep] do proprietario";
        }
        if(cliente.getEndereco_cep().length() != 9){
            return "Cep [" + cliente.getEndereco_cep().toString() + "] Informado inválido";
        }
        if(cliente.getEndereco_cidade() == ""){
            return "Deve ser informado o Endereco [Cidade] do proprietario";
        }
        if(cliente.getEndereco_uf() == ""){
            return "Deve ser informado o Endereco [UF] do proprietario";
        }
        return "OK";
    }

      /* ******** Salvar ********* */ 
    @Transactional
    public void salvarClientes(pcp_cliente cliente, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        String validacao = validaCliente(cliente);

        if (!validacao.equals("OK")) throw new RuntimeException(validacao);

        pcp_cliente clienteAnalise = clienteRepository.findByCodcliente(cliente.getCodcliente());
        if(clienteAnalise == null) clienteAnalise = new pcp_cliente();

        clienteAnalise.setEmail(cliente.getEmail());
        clienteAnalise.setEndereco_bairro(cliente.getEndereco_bairro());
        clienteAnalise.setEndereco_numero(cliente.getEndereco_numero());
        clienteAnalise.setEndereco_logradouro(cliente.getEndereco_logradouro());
        clienteAnalise.setEndereco_cep(cliente.getEndereco_cep());
        clienteAnalise.setEndereco_cidade(cliente.getEndereco_cidade());
        clienteAnalise.setEndereco_uf(cliente.getEndereco_uf());
        clienteAnalise.setEndereco_complemento(cliente.getEndereco_complemento());
        clienteAnalise.setNumtel(cliente.getNumtel());
        clienteAnalise.setAtualizado_em(LocalDate.now());

        if(cliente.getCodcliente() == null){
            clienteAnalise.setNome(cliente.getNome());
            clienteAnalise.setPessoa_fisica(cliente.isPessoa_fisica());
            clienteAnalise.setDocumento(cliente.getDocumento());
            clienteAnalise.setCriado_em(LocalDate.now());
            clienteAnalise.setId_usuario(cliente.getId_usuario());
        } 

        clienteRepository.save(clienteAnalise);
    }
    /* ***************** */ 

    /* ********* GRIDS ******** */ 
    public List<?> buscarClientes(Integer codcliente){
        List<pcp_cliente> clientes =  contratosCustomRepository.buscarClientes(codcliente);

        utilForm.initGrid();
        for (int i = 0; i < clientes.size(); i++) {
            String enderecocompl = clientes.get(i).getEndereco_bairro() + ", "
                                 + clientes.get(i).getEndereco_numero() + ", "
                                 + clientes.get(i).getEndereco_cidade() + ", "
                                 + clientes.get(i).getEndereco_uf();

            String documento = clientes.get(i).isPessoa_fisica()? utilForm.formatDocToCpf(clientes.get(i).getDocumento()) : utilForm.formatDocToCnpj(clientes.get(i).getDocumento());

            utilForm.criarRow();
            utilForm.criarColuna(clientes.get(i).getCodcliente().toString());
            utilForm.criarColuna(clientes.get(i).getNome());
            utilForm.criarColuna(documento);
            utilForm.criarColuna(enderecocompl);
            utilForm.criarColuna(clientes.get(i).getNumtel().toString());
            utilForm.criarColuna(clientes.get(i).getEmail());
            utilForm.criarColuna(clientes.get(i).getEndereco_logradouro());
            utilForm.criarColuna(clientes.get(i).getEndereco_cidade());
            utilForm.criarColuna(clientes.get(i).getEndereco_numero());
            utilForm.criarColuna(clientes.get(i).getEndereco_bairro());
            utilForm.criarColuna(clientes.get(i).getEndereco_uf());
            utilForm.criarColuna(clientes.get(i).getEndereco_cep());
            utilForm.criarColuna(Boolean.toString(clientes.get(i).isPessoa_fisica()));
        }

        return utilForm.criarGrid();
    }

}
