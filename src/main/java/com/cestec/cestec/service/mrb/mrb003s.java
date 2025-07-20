package com.cestec.cestec.service.mrb;

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
            utilForm.criarRow();
            utilForm.criarColuna(func.get(i).getCodfunc().toString());
            utilForm.criarColuna(func.get(i).getNomefunc());
            utilForm.criarColuna(func.get(i).getCodsetor().toString());
            utilForm.criarColuna(func.get(i).getNomesetor());
            utilForm.criarColuna(func.get(i).getCodcargo().toString());
            utilForm.criarColuna(func.get(i).getNomecargo());
            utilForm.criarColuna(func.get(i).getNomecargo());
        }

        return utilForm.criarGrid();
    }
}
