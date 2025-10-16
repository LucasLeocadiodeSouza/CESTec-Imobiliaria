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
import com.cestec.cestec.model.opr.opr_versionamento;
import com.cestec.cestec.model.opr.opr_versionamentoId;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.opr.chamadosSolicRepo;
import com.cestec.cestec.repository.opr.opr002Repo;
import com.cestec.cestec.repository.opr.versionamentoRepo;
import com.cestec.cestec.repository.pagamento.contaRepository;
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
    private versionamentoRepo versionamentoRepo;

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

    public List<modelUtilForm> getOptionsComplex(){
        List<modelUtilForm> listaMotivos = new java.util.ArrayList<>();
        int i = 1;

        listaMotivos.add(new modelUtilForm(0, "Selecione a complexidade"));

        do{
            listaMotivos.add(new modelUtilForm(i, getComplexidade(i)));
            i++;
        }while (getComplexidade(i) != "");
        
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

            if(solicitacao.getTitulo() == null || solicitacao.getTitulo().isBlank()) return ResponseEntity.badRequest().body("Deve ser informado um titulo para a solicitação");

            if(solicitacao.getDescricao() == null || solicitacao.getDescricao().isBlank())  return ResponseEntity.badRequest().body("Deve ser informado uma Descricão para cadastrar a solicitação");

            if(solicitacao.getPrioridade() == null || solicitacao.getPrioridade() == 0)  return ResponseEntity.badRequest().body("Deve ser informado uma prioridade para cadastrar a solicitação");
            if(getPrioridade(solicitacao.getPrioridade()).isBlank()) return ResponseEntity.badRequest().body("Prioridade da solicitação invalido!");

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
            return ResponseEntity.internalServerError().body("Erro ao Enviar a solicitação para fila: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> direcionarSolic(String ideusu, String ideusudirec, Integer complex, Integer idsolic){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(idsolic);
            if (solicanalise == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitacão com o codigo '" + idsolic + "'");

            funcionario func = funcionarioRepo.findFuncByIdeusu(ideusudirec);
            solicanalise.setCodfuncfila(func);
            solicanalise.setComplex(complex);

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Direcionar a solicitação: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> finalizarSolicitacao(String ideusu, String obs, Integer idsolic){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            if(obs == null || obs.isBlank()) return ResponseEntity.badRequest().body("Deve ser fornecido uma observação para Finalizar a Solicitação!");
            
            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(idsolic);
            if (solicanalise == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitação com o codigo '" + idsolic + "'");
            
            solicanalise.setEstado(3);
            solicanalise.setObs(obs);
            solicanalise.setDatconcl(LocalDate.now());

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Finalizar a solicitação: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> incluirVersionamento(String ideusu, Integer idsolic, Integer merge, String branch, String prog){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_chamados_solic chamado = chamadosSolicRepo.findByIdSolic(idsolic);
            if (chamado == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitação com o codigo '" + idsolic + "'");

            if(chamado.getEstado() == 3 && chamado.getEstado() == 4) return ResponseEntity.badRequest().body("A Solicitação com o codigo '" + idsolic + "' já foi finalizada, não sendo mais possível incluir algum versionamento.");

            opr_versionamento versionaAnalise = opr002repo.getVersionamentoById(merge,branch,prog,idsolic);
            if (versionaAnalise != null) return ResponseEntity.badRequest().body("Já existe um versionamento com os campos preenchidos [merge '" + merge + "', branch '" + branch + "', programa '" + prog + "']");

            if(merge == null  || merge == 0)  return ResponseEntity.badRequest().body("Deve ser informado o Codigo do Merge para cadastrar o versionamento");
            if(branch == null || branch.isBlank())  return ResponseEntity.badRequest().body("Deve ser informado o nome da branch para o versionamento");
            if(prog == null   || prog.isBlank())  return ResponseEntity.badRequest().body("Deve ser informado o programa do versionamento");

            versionaAnalise = new opr_versionamento();
            versionaAnalise.setChamadoSolic(chamado);
            versionaAnalise.setDatregistro(LocalDate.now());
            versionaAnalise.setIdeusu(ideusu);
            versionaAnalise.setSeq(new opr_versionamentoId(merge, branch, prog, idsolic));

            versionamentoRepo.save(versionaAnalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Direcionar a solicitação: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> iniciarSolicitacao(String ideusu, Integer idsolic){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(idsolic);
            if (solicanalise == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitação com o codigo '" + idsolic + "'");
            
            solicanalise.setEstado(2);

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Finalizar a solicitação: " + e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> cancelarSolicitacao(String ideusu, Integer idsolic){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_chamados_solic solicanalise = chamadosSolicRepo.findByIdSolic(idsolic);
            if (solicanalise == null) return ResponseEntity.badRequest().body("Não encontrado a Solicitação com o codigo '" + idsolic + "'");
            
            if(solicanalise.getEstado() != 1) return ResponseEntity.badRequest().body("Não é possível cancelar a Solicitação com o codigo '" + idsolic + "' pois a mesma não se encontra em uma estado que permita seu cancelamento ('" + getEstado(1) + "')");

            solicanalise.setEstado(4);

            chamadosSolicRepo.save(solicanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao Finalizar a solicitação: " + e.getMessage());    
        }
    }

    /* ********** GRIDS ********** */

    public List<?> carregarGridChamados(String ideusu, Integer somenteAtivo, String acao){
        boolean vsomenteAtivo = somenteAtivo == 1;

        List<chamadoSolicDTO> solic = opr002repo.buscarChamados(ideusu, vsomenteAtivo, acao);

        utilForm.initGrid();
        for (int i = 0; i < solic.size(); i++) {
            Date dataCriacao = Date.valueOf(LocalDate.now()); // llll
            Date dataConcl   = Date.valueOf(LocalDate.now()); // llll
            Date dataPrev    = Date.valueOf(LocalDate.now()); // llll

            if(solic.get(i).getDatregistro() != null) dataCriacao = Date.valueOf(solic.get(i).getDatregistro());
            if(solic.get(i).getDatconcl() != null)    dataConcl   = Date.valueOf(solic.get(i).getDatconcl());
            if(solic.get(i).getPrevisao() != null)    dataPrev    = Date.valueOf(solic.get(i).getPrevisao());

            boolean podeAlterar = solic.get(i).getEstado() != 3;

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
            utilForm.criarColuna(solic.get(i).getNomeusufila());
            utilForm.criarColuna(gen.findCodSetorByIdeusu(solic.get(i).getIdeusufila()));
            utilForm.criarColuna(gen.findSetorByIdeusu(solic.get(i).getIdeusufila()));
            utilForm.criarColuna(gen.findCodSetorByIdeusu(solic.get(i).getIdeususolic()));
            utilForm.criarColuna(gen.findSetorByIdeusu(solic.get(i).getIdeususolic()));
            utilForm.criarColuna(Boolean.toString(podeAlterar));
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

    public List<?> carregarGridversionamento(Integer idsolic){
        List<opr_versionamento> versiona = versionamentoRepo.findByChamadoSolicId(idsolic);

        utilForm.initGrid();
        for (int i = 0; i < versiona.size(); i++) {
            Date dataCriacao = Date.valueOf(LocalDate.now());
            
            if(versiona.get(i).getDatregistro() != null) dataCriacao = Date.valueOf(versiona.get(i).getDatregistro());

            utilForm.criarRow();
            utilForm.criarColuna(versiona.get(i).getSeq().getCodmerge().toString());
            utilForm.criarColuna(versiona.get(i).getSeq().getBranch_name());
            utilForm.criarColuna(versiona.get(i).getSeq().getProg());
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataCriacao));
            utilForm.criarColuna(versiona.get(i).getIdeusu());
        }

        return utilForm.criarGrid();
    }
}
