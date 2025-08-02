package com.cestec.cestec.service.opr;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.cestec.cestec.model.cargo;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.funcionarioDTO;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.opr.agendamentoDTO;
import com.cestec.cestec.model.opr.opr_agendamentos;
import com.cestec.cestec.model.opr.opr_agendamentos_func;
import com.cestec.cestec.repository.cargoRepository;
import com.cestec.cestec.repository.setorRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.generico.roleacessRepository;
import com.cestec.cestec.repository.opr.agendamentosFuncRepo;
import com.cestec.cestec.repository.opr.agendamentosRepo;
import com.cestec.cestec.repository.opr.opr001repo;
import com.cestec.cestec.service.sp_notificacaoService;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class opr001s {
    @Autowired
    private sp_userService sp_user;

    @Autowired
    private setorRepository setorRepository;

    @Autowired
    private cargoRepository cargoRepository;

    @Autowired
    private opr001repo opr001repo;
    
    @Autowired
    private roleacessRepository roleacess;

    @Autowired
    private agendamentosRepo agendRepo;

    @Autowired
    private funcionarioRepository funcionarioRepo;

    @Autowired
    private agendamentosFuncRepo agendFuncRepo;

    @Autowired
    private sp_notificacaoService notificaService;


    /* ********** Funcoes ********** */


    public static String getMotivo(Integer codmotivo){
        switch (codmotivo) {
        case 1: return "Recado";
        case 2: return "Palestra";
        case 3: return "Reunião";
        }
        return "";
    }

    public List<?> carregarGridFucnionarios(Integer codagend, String nomefunc, String acao){
        List<funcionarioDTO> func = opr001repo.buscarFuncionarios(nomefunc);

        utilForm.initGrid();
        for (int i = 0; i < func.size(); i++) {
            boolean marcar = false;
            if(agendFuncRepo.findFuncInAgend(codagend, func.get(i).getCodfunc()) != null) marcar = true;

            utilForm.criarRow();
            utilForm.criarColuna("<input type='checkbox' id='funcionario_" + i + "' name='funcionario_" + i + "'" + (marcar?"checked":"") + " "+ (acao.equals("Consultando")?"disabled":"") + ">");
            utilForm.criarColuna(func.get(i).getCodfunc().toString());
            utilForm.criarColuna(func.get(i).getNomefunc());
            utilForm.criarColuna(func.get(i).getCodcargo().toString());
            utilForm.criarColuna(func.get(i).getNomecargo());
            utilForm.criarColuna(func.get(i).getCodsetor().toString());
            utilForm.criarColuna(func.get(i).getNomesetor());
        }

        return utilForm.criarGrid();
    }

    public List<?> carregarGridAgendamentos(Integer codfunc, Integer codcargo, Integer codsetor){
        List<agendamentoDTO> agenda = opr001repo.buscarAgendamentos(codfunc, codcargo, codsetor);

        utilForm.initGrid();
        for (int i = 0; i < agenda.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(agenda.get(i).getCodagenda().toString());
            utilForm.criarColuna(agenda.get(i).getTitulo());
            utilForm.criarColuna(agenda.get(i).getDescricao());
            utilForm.criarColuna(getMotivo(agenda.get(i).getMotivo()));
            utilForm.criarColuna(agenda.get(i).getDatagen().toString());
            utilForm.criarColuna(agenda.get(i).getHoragen2().toString());
            utilForm.criarColuna(agenda.get(i).getIdeusu());
            utilForm.criarColuna(agenda.get(i).getMotivo().toString());
            utilForm.criarColuna(agendFuncRepo.findAllByCodAgend(agenda.get(i).getCodagenda()).toString());
        }

        return utilForm.criarGrid();
    }

    public List<opr_agendamentos_func> buscarAgendamentosFunc(String ideusu){
        return agendFuncRepo.findAllByCodFunc(ideusu);
    }

    public List<modelUtilForm> getOptionsMotivo(){
        List<modelUtilForm> listaMotivos = new java.util.ArrayList<>();
        int i = 1;

        do{
            listaMotivos.add(new modelUtilForm(i, getMotivo(i)));
            i++;
        }while (getMotivo(i) != "");
        
        return listaMotivos;
    }

    public List<modelUtilForm> getOptionsCargo(){
        List<cargo> AllCargos           = cargoRepository.findAll();
        List<modelUtilForm> listaCargos = new java.util.ArrayList<>();

        listaCargos.add(new modelUtilForm(0, "Todos os Cargos"));

        for (int i = 1; i < AllCargos.size(); i++) {
            listaCargos.add(new modelUtilForm(AllCargos.get(i).getId(), AllCargos.get(i).getNome()));
        }
        
        return listaCargos;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> cadastrarAgendamento(agendamentoDTO agendamento){
        try{
            if(sp_user.loadUserByUsername(agendamento.getIdeusu()) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            opr_agendamentos agendanalise = agendRepo.findAgenByCodAgend(agendamento.getCodagenda());
            if (agendanalise == null) agendanalise = new opr_agendamentos();

            if(agendamento.getTitulo() == null || agendamento.getTitulo() == "") return ResponseEntity.badRequest().body("Deve ser informado um titulo para o agendamento");

            if(agendamento.getMotivo() == null || agendamento.getMotivo() == 0)  return ResponseEntity.badRequest().body("Deve ser informado um motivo para cadastrar o agendamento");
            if(getMotivo(agendamento.getMotivo()) == "") return ResponseEntity.badRequest().body("Motivo de agendamento invalido!");

            if(agendamento.getDatagen() == null || agendamento.getDatagen().isBefore(LocalDate.now())) return ResponseEntity.badRequest().body("Deve ser informado uma data para o agendamento");
            if(agendamento.getHoragen() == null) return ResponseEntity.badRequest().body("Deve ser informado um horario de agendamento.");

            agendanalise.setTitulo(agendamento.getTitulo());
            agendanalise.setDescricao(agendamento.getDescricao());
            agendanalise.setMotivo(agendamento.getMotivo());
            agendanalise.setDatagen(agendamento.getDatagen());
            agendanalise.setHoragen(LocalTime.parse(agendamento.getHoragen()));

            if(agendamento.getCodagenda() == null || agendamento.getCodagenda() == 0){
                agendanalise.setDatiregistro(LocalDate.now());

                agendanalise.setIdeusu(agendamento.getIdeusu());
            }

            agendRepo.save(agendanalise);
            return ResponseEntity.ok(agendanalise.getId());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar o agendamento: " + e.getMessage());    
        }
    }

    public ResponseEntity<?> validaAgendaFunc(agendamentoDTO agendamento){
        if(sp_user.loadUserByUsername(agendamento.getIdeusu()) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

        if(!agendRepo.existsById(agendamento.getCodagenda())) return ResponseEntity.badRequest().body("Agendamento não encontrado para vincular o funcionario!");
        if(!funcionarioRepo.existsById(agendamento.getCodfunc())) return ResponseEntity.badRequest().body("Funcionario não encontrado para vincular ao agendamento!");

        return ResponseEntity.ok().body("OK");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> vincularAgendamentoFunc(agendamentoDTO agendamento){
        try{
            ResponseEntity<?> valida = validaAgendaFunc(agendamento);
            if(valida.getBody() != "OK") return valida;

            opr_agendamentos_func agendfuncanalise = agendFuncRepo.findByCodAgendFunc(agendamento.getCodagendfunc());
            if (agendfuncanalise == null) agendfuncanalise = new opr_agendamentos_func();
            
            opr_agendamentos agend = agendRepo.findAgenByCodAgend(agendamento.getCodagenda());
            agendfuncanalise.setCodagenda(agend);

            funcionario func = funcionarioRepo.findFuncBycodfunc(agendamento.getCodfunc());
            agendfuncanalise.setCodfunc(func);

            if(agendamento.getCodagendfunc() == null || agendamento.getCodagendfunc() == 0){
                agendfuncanalise.setDatiregistro(LocalDate.now());

                agendfuncanalise.setIdeusu(agendamento.getIdeusu());

                notificaService.criarNotificacao(func.getSp_user().getLogin(), "Novo agendamento disponível! " + agendamento.getTitulo(), agendamento.getIdeusu());
            }
            agendFuncRepo.save(agendfuncanalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,  "Erro ao vincular agendamento", e);  
        }
    }
}
