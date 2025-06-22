package com.cestec.cestec.service.pagamento;

import static org.mockito.Answers.valueOf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.contasAPagar.faturamentoDTO;
import com.cestec.cestec.repository.pagamento.pagamentoCustomRepo;
import com.cestec.cestec.util.utilForm;

@Service
public class pga001s {
    
    @Autowired
    private pagamentoCustomRepo pagamentoCustomRepo;

    public List<?> buscarFaturaCliente(){
        List<faturamentoDTO> faturamento = pagamentoCustomRepo.buscarFaturaCliente();

        utilForm.initGrid();
        for (int i = 0; i < faturamento.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(faturamento.get(i).getCodcliente().toString());
            utilForm.criarColuna(faturamento.get(i).getNomecliente());
            utilForm.criarColuna(faturamento.get(i).getDocumento());
            utilForm.criarColuna(faturamento.get(i).getCodcontrato().toString());
            utilForm.criarColuna(faturamento.get(i).getCodfatura().toString());
            utilForm.criarColuna(String.valueOf(faturamento.get(i).getValor()));
            utilForm.criarColuna(faturamento.get(i).getVencimento().toString());
            utilForm.criarColuna(faturamento.get(i).getTipopag().name());
            utilForm.criarColuna(faturamento.get(i).getSituacao().name());
            utilForm.criarColuna(faturamento.get(i).getNumdoc());
            utilForm.criarColuna(faturamento.get(i).getCodconta().toString());
        }

        return utilForm.criarGrid();
    }

}
