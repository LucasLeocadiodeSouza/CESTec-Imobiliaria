package com.cestec.cestec.service.opr;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.opr.chamadoSolicDTO;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.opr.chamadosSolicRepo;
import com.cestec.cestec.repository.opr.opr002Repo;
import com.cestec.cestec.util.utilForm;

@Service
public class opr002s {
    @Autowired
    private funcionarioRepository funcionarioRepo;

    @Autowired
    private chamadosSolicRepo chamadosSolicRepo;

    @Autowired
    private opr002Repo opr002repo;

    /* ********** Funcoes ********** */

    public String getComplexidade(Integer codcomplex){
        switch (codcomplex) {
        case 1: return "Baixa";
        case 2: return "Média";
        case 3: return "Alta";
        }
        return "";
    }

    public String getEstado(Integer codestado){
        switch (codestado) {
        case 1: return "Direcionado";
        case 2: return "Iniciado";
        case 3: return "Finalizado";
        case 4: return "Cancelado";
        }
        return "";
    }

     public String getPrioridade(Integer codprioridade){
        switch (codprioridade) {
        case 1: return "Baixa";
        case 2: return "Moderada";
        case 3: return "Alta";
        case 4: return "Maxima";
        case 5: return "Extrema";
        }
        return "";
    }

    /* ********** GRIDS ********** */

    public List<?> carregarGridChamados(String ideusu, Integer somenteAtivo){
        boolean vsomenteAtivo = somenteAtivo == 1;

        List<chamadoSolicDTO> solic = opr002repo.buscarChamados(ideusu, vsomenteAtivo);

        utilForm.initGrid();
        for (int i = 0; i < solic.size(); i++) {
            Date dataCriacao = Date.valueOf(LocalDate.now());
            Date dataConcl   = Date.valueOf(LocalDate.now());
            Date dataPrev    = Date.valueOf(LocalDate.now());

            if(solic.get(i).getDatregistro() != null) dataCriacao = Date.valueOf(solic.get(i).getDatregistro());
            if(solic.get(i).getDatconcl() != null)    dataConcl   = Date.valueOf(solic.get(i).getDatconcl());
            if(solic.get(i).getPrevisao() != null)    dataPrev    = Date.valueOf(solic.get(i).getPrevisao());

            utilForm.criarRow();
            utilForm.criarColuna(solic.get(i).getIdsolic().toString());
            utilForm.criarColuna(solic.get(i).getTitulo());
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataCriacao));
            utilForm.criarColuna(getEstado(solic.get(i).getEstado()));
            utilForm.criarColuna(getPrioridade(solic.get(i).getPrioridade()));
            utilForm.criarColuna(solic.get(i).getIdeususolic());
            utilForm.criarColuna(solic.get(i).getDescricao());
            utilForm.criarColuna(solic.get(i).getPrioridade().toString());
            utilForm.criarColuna(solic.get(i).getComplex().toString());
            utilForm.criarColuna(getComplexidade(solic.get(i).getComplex()));
            utilForm.criarColuna(solic.get(i).getEstado().toString());
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataConcl));
            utilForm.criarColuna(solic.get(i).getFeedback());
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataPrev));
            utilForm.criarColuna(solic.get(i).getObs());
            utilForm.criarColuna(solic.get(i).getIdeusufila());
        }

        return utilForm.criarGrid();
    }

}
