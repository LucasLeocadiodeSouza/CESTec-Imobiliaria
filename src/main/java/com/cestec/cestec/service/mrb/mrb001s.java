package com.cestec.cestec.service.mrb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.repository.custom.prjCadastroAplicacaoRepository;
import com.cestec.cestec.util.utilForm;

@Service
public class mrb001s {

    @Autowired
    private prjCadastroAplicacaoRepository cadasaplcustomrepo;

    public List<?> buscarAplicacoesGrid(Integer codapl, Integer codmodu, String ideusu){
        List<sp_aplicacoes> aplicacao = cadasaplcustomrepo.buscarAplicacoes(codapl,codmodu, ideusu);

        utilForm.initGrid();
        for (int i = 0; i < aplicacao.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(aplicacao.get(i).getIdeusu());
            utilForm.criarColuna(aplicacao.get(i).getModulo().getId().toString());
            utilForm.criarColuna(aplicacao.get(i).getModulo().getDescricao());
            utilForm.criarColuna(aplicacao.get(i).getId().toString());
            utilForm.criarColuna(aplicacao.get(i).getDescricao());
            utilForm.criarColuna(aplicacao.get(i).getDatregistro().toString());
        }

        return utilForm.criarGrid();
    }

}
