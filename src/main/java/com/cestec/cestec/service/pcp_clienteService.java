package com.cestec.cestec.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cestec.cestec.model.pcp_cliente;
import com.cestec.cestec.repository.clienteRepository;
import com.cestec.cestec.repository.prjContratosCustomRepository;
import com.cestec.cestec.util.utilForm;

@Service
public class pcp_clienteService {
    
    @Autowired
    private clienteRepository clienteRepository;

    @Autowired
    private prjContratosCustomRepository contratosCustomRepository;

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

    @Transactional
    public ResponseEntity<?> salvarClientes(pcp_cliente cliente){
        String validacao = validaCliente(cliente);        
        if (!validacao.equals("OK")) {
            return ResponseEntity.ok(validacao);
        }

        try{
            pcp_cliente clienteAnalise = clienteRepository.findByCodcliente(cliente.getCodcliente());
            if(!existeCliente(cliente.getCodcliente())) clienteAnalise = new pcp_cliente();

            clienteAnalise.setDocumento(cliente.getDocumento());
            clienteAnalise.setEmail(cliente.getEmail());
            clienteAnalise.setEndereco_bairro(cliente.getEndereco_bairro());
            clienteAnalise.setEndereco_numero(cliente.getEndereco_numero());
            clienteAnalise.setEndereco_logradouro(cliente.getEndereco_logradouro());
            clienteAnalise.setEndereco_cep(cliente.getEndereco_cep());
            clienteAnalise.setEndereco_cidade(cliente.getEndereco_cidade());
            clienteAnalise.setEndereco_uf(cliente.getEndereco_uf());
            clienteAnalise.setEndereco_complemento(cliente.getEndereco_complemento());
            clienteAnalise.setNome(cliente.getNome());
            clienteAnalise.setNumtel(cliente.getNumtel());
            clienteAnalise.setCriado_em(clienteAnalise.getCriado_em() == null?LocalDateTime.now():clienteAnalise.getCriado_em());
            clienteAnalise.setAtualizado_em(LocalDateTime.now());

            if(!existeCliente(clienteAnalise.getCodcliente())) clienteAnalise.setId_usuario(cliente.getId_usuario());

            clienteRepository.save(clienteAnalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());    
        }
    }

    public List<?> buscarClientes(Integer codcliente){
        List<pcp_cliente> clientes =  contratosCustomRepository.buscarClientes(codcliente);

        utilForm.initGrid();
        for (int i = 0; i < clientes.size(); i++) {
            String enderecocompl = clientes.get(i).getEndereco_bairro() + ", " + clientes.get(i).getEndereco_numero() + ", " + clientes.get(i).getEndereco_cidade() + ", " + clientes.get(i).getEndereco_uf();

            utilForm.criarRow();
            utilForm.criarColuna(clientes.get(i).getCodcliente().toString());
            utilForm.criarColuna(clientes.get(i).getNome());
            utilForm.criarColuna(clientes.get(i).getDocumento().toString());
            utilForm.criarColuna(enderecocompl);
            utilForm.criarColuna(clientes.get(i).getNumtel().toString());
            utilForm.criarColuna(clientes.get(i).getEmail());
            utilForm.criarColuna(clientes.get(i).getEndereco_logradouro());
            utilForm.criarColuna(clientes.get(i).getEndereco_cidade());
            utilForm.criarColuna(clientes.get(i).getEndereco_numero());
            utilForm.criarColuna(clientes.get(i).getEndereco_bairro());
            utilForm.criarColuna(clientes.get(i).getEndereco_uf());
            utilForm.criarColuna(clientes.get(i).getEndereco_cep());
        }

        return utilForm.criarGrid();
    }

    public pcp_cliente buscarClienteGrid(Integer index){
        return clienteRepository.findAll().get(index);
    }

    public String findNomeClienteById(Integer codcliente){
        return clienteRepository.findById(codcliente).get().getNome();
    }

    public Boolean existeCliente(Integer codcliente){
        return clienteRepository.findByCodcliente(codcliente) != null;
    }
}
