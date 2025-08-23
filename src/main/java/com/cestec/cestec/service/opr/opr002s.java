package com.cestec.cestec.service.opr;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.opr.chamadoSolicDTO;
import com.cestec.cestec.model.opr.opr_chamados_solic;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.opr.chamadosSolicRepo;
import com.cestec.cestec.repository.opr.opr002Repo;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class opr002s {
    @Autowired
    private funcionarioRepository funcionarioRepo;

    @Autowired
    private chamadosSolicRepo chamadosSolicRepo;

    @Autowired
    private opr002Repo opr002repo;

    @Autowired
    private sp_userService sp_user;

    @Autowired
    private genService gen;

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
        case 0: return "Aguardando Env.";
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

    public List<modelUtilForm> getOptionsPrioridade(){
        List<modelUtilForm> listaMotivos = new java.util.ArrayList<>();
        int i = 1;

        listaMotivos.add(new modelUtilForm(0, "Selecione a Prioridade"));

        do{
            listaMotivos.add(new modelUtilForm(i, getPrioridade(i)));
            i++;
        }while (getPrioridade(i) != "");
        
        return listaMotivos;
    }

    public String getNomeUsuario(String ideusu){
        return gen.getNomeByIdeusu(ideusu);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> abrirSolicitacao(chamadoSolicDTO solicitacao){
        try{
            if(sp_user.loadUserByUsername(solicitacao.getIdeususolic()) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(solicitacao.getIdsolic());
            if (solicanalise == null) solicanalise = new opr_chamados_solic();

            if(solicitacao.getTitulo() == null || solicitacao.getTitulo() == "") return ResponseEntity.badRequest().body("Deve ser informado um titulo para a solicitacao");

            if(solicitacao.getDescricao() == null || solicitacao.getDescricao() == "")  return ResponseEntity.badRequest().body("Deve ser informado uma Descricão para cadastrar a solicitacão");

            if(solicitacao.getPrioridade() == null || solicitacao.getPrioridade() == 0)  return ResponseEntity.badRequest().body("Deve ser informado uma prioridade para cadastrar a solicitacao");
            if(getPrioridade(solicitacao.getPrioridade()) == "") return ResponseEntity.badRequest().body("Prioridade da solicitacão invalido!");

            solicanalise.setTitulo(solicitacao.getTitulo());
            solicanalise.setDescricao(solicitacao.getDescricao());
            solicanalise.setPrioridade(solicitacao.getPrioridade());

            funcionario funcFila = funcionarioRepo.findFuncBycodfunc(6);
            solicanalise.setCodfuncfila(funcFila);

            if(solicitacao.getIdsolic() == null || solicitacao.getIdsolic() == 0){
                solicanalise.setEstado(0);
                solicanalise.setComplex(0);
                solicanalise.setDatregistro(LocalDate.now());
                solicanalise.setIdeususolic(solicitacao.getIdeususolic());
            }

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar o solicitacao: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> enviarSolicitacao(String ideusu, Integer idsolic){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(idsolic);
            if (solicanalise == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitacão com o codigo '" + idsolic + "'");

            solicanalise.setEstado(1);

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Enviar a solicitacao para fila: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> direcionarSolic(String ideusu, String ideusudirec, Integer idsolic){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(idsolic);
            if (solicanalise == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitacão com o codigo '" + idsolic + "'");

            funcionario func = funcionarioRepo.findFuncByIdeusu(ideusudirec);
            solicanalise.setCodfuncfila(func);

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Direcionar a solicitacao: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> finalizarSolicitacao(String ideusu, String obs, Integer idsolic){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            if(obs == null || obs.isBlank()) return ResponseEntity.badRequest().body("Deve ser fornecido uma observacão para Finalizar a Solicitacão!");
            
            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(idsolic);
            if (solicanalise == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitacão com o codigo '" + idsolic + "'");
                
            solicanalise.setEstado(4);
            solicanalise.setObs(obs);
            solicanalise.setDatconcl(LocalDate.now());

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Finalizar a solicitacao: " + e.getMessage());    
        }
    }

    /* ********** GRIDS ********** */

    public List<?> carregarGridChamados(String ideusu, Integer somenteAtivo, String acao){
        boolean vsomenteAtivo = somenteAtivo == 1;

        List<chamadoSolicDTO> solic = opr002repo.buscarChamados(ideusu, vsomenteAtivo, acao);

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

    public List<?> carregarGridChamSolicitados(String ideusu, Integer somenteAtivo){
        boolean vsomenteAtivo = somenteAtivo == 1;

        List<chamadoSolicDTO> solic = opr002repo.buscarSolicitacoes(ideusu, vsomenteAtivo);

        utilForm.initGrid();
        for (int i = 0; i < solic.size(); i++) {
            Date dataCriacao = Date.valueOf(LocalDate.now());
            
            if(solic.get(i).getDatregistro() != null) dataCriacao = Date.valueOf(solic.get(i).getDatregistro());

            utilForm.criarRow();
            utilForm.criarColuna(solic.get(i).getIdsolic().toString());
            utilForm.criarColuna(solic.get(i).getTitulo()); 
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataCriacao));
            utilForm.criarColuna(getEstado(solic.get(i).getEstado()));
            utilForm.criarColuna(getPrioridade(solic.get(i).getPrioridade()));
            utilForm.criarColuna(solic.get(i).getIdeususolic());
            utilForm.criarColuna(solic.get(i).getDescricao());
            utilForm.criarColuna(solic.get(i).getPrioridade().toString());
            utilForm.criarColuna(solic.get(i).getEstado().toString());
            utilForm.criarColuna(solic.get(i).getIdeusufila());
        }

        return utilForm.criarGrid();
    }

}
