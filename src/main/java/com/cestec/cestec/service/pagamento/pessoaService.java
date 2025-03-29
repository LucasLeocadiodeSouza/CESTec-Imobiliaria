package com.cestec.cestec.service.pagamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.contasAPagar.Pessoa;
import com.cestec.cestec.repository.pagamento.pessoaRepository;

@Service
public class pessoaService {
    
    @Autowired
    private pessoaRepository pessoaRepository;

    public List<Pessoa> listarPessoas(){
        return pessoaRepository.findAll();
    }

}
