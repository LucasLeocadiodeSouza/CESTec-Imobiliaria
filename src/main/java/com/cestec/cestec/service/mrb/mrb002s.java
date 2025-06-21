package com.cestec.cestec.service.mrb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.repository.custom.prjCadastroAplicacaoRepository;
import com.cestec.cestec.util.utilForm;

@Service
public class mrb002s {
    
    @Autowired
    private prjCadastroAplicacaoRepository cadasaplcustomrepo;

    public List<?> buscarTabelas(String nomeTabela){
        List<String> tabelas = cadasaplcustomrepo.buscarTabelas(nomeTabela);

        utilForm.initGrid();
        for (int i = 0; i < tabelas.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(tabelas.get(i));
        }

        return utilForm.criarGrid();
    }

    public List<?> buscarColunas(String nomeTabela) throws SQLException{
        Map<String, Object[]> colunas = cadasaplcustomrepo.buscarColunas(nomeTabela);

        utilForm.initGrid();

        colunas.forEach((column, array) -> {
            utilForm.criarRow();
            utilForm.criarColuna(column);
            utilForm.criarColuna(array[0].toString());
            utilForm.criarColuna(array[1].toString());
            utilForm.criarColuna(array[2].toString());
            utilForm.criarColuna(array[3].toString());
        });

        return utilForm.criarGrid(); 
    }

    public List<?> buscarIndexs(String nomeTabela) throws SQLException{
        Map<String, Object[]> colunas = cadasaplcustomrepo.buscarIndexs(nomeTabela);

        utilForm.initGrid();

        colunas.forEach((index, array) -> {
            utilForm.criarRow();
            utilForm.criarColuna(index);
            utilForm.criarColuna(array[0].toString());
            utilForm.criarColuna(array[1].toString());
            utilForm.criarColuna(array[2].toString());
        });

        return utilForm.criarGrid();
    }
}
