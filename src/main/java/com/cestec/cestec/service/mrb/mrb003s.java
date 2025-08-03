package com.cestec.cestec.service.mrb;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.funcionarioDTO;
import com.cestec.cestec.repository.cargoRepository;
import com.cestec.cestec.repository.setorRepository;
import com.cestec.cestec.repository.custom.prjControleDeUsuarioRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class mrb003s {
    @Autowired
    private funcionarioRepository funcionarioRepo;

    @Autowired
    private prjControleDeUsuarioRepository prjControleDeUsuarioRepository;

    @Autowired
    private sp_userService sp_user;

    @Autowired
    private setorRepository setorRepository;

    @Autowired
    private cargoRepository cargoRepository;


    public List<?> carregarGridFucnionarios(String nomefunc){
        List<funcionarioDTO> func = prjControleDeUsuarioRepository.buscarFuncionarios(nomefunc);
        
        
        utilForm.initGrid();
        for (int i = 0; i < func.size(); i++) {
            Date dataCriacao = Date.valueOf(func.get(i).getCriado_em());

            utilForm.criarRow();
            utilForm.criarColuna(func.get(i).getCodfunc().toString());
            utilForm.criarColuna(func.get(i).getNomefunc());
            utilForm.criarColuna(func.get(i).getCodsetor().toString());
            utilForm.criarColuna(func.get(i).getNomesetor());
            utilForm.criarColuna(func.get(i).getCodcargo().toString());
            utilForm.criarColuna(func.get(i).getNomecargo());
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataCriacao));
            utilForm.criarColuna(func.get(i).getDatinasc().toString());
            utilForm.criarColuna(func.get(i).getCpf());
            utilForm.criarColuna(func.get(i).getEndereco());
            utilForm.criarColuna(String.valueOf(func.get(i).getSalario()));
            utilForm.criarColuna(func.get(i).getCriado_em().toString());
        }

        return utilForm.criarGrid();
    }
}
