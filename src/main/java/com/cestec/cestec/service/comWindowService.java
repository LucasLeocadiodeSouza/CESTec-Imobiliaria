package com.cestec.cestec.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.historicoAcessoAplDTO;
import com.cestec.cestec.model.pcp_meta;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.model.sp_histacessapl;
import com.cestec.cestec.model.sp_modulos;
import com.cestec.cestec.model.cri.corretorDTO;
import com.cestec.cestec.model.opr.agendamentoDTO;
import com.cestec.cestec.model.opr.opr_agendamentos_func;
import com.cestec.cestec.model.spf.sp_notificacao_usu;
import com.cestec.cestec.repository.histAcessAplRepo;
import com.cestec.cestec.repository.metaRepository;
import com.cestec.cestec.repository.cri.contratoRepository;
import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.generico.modulosRepository;
import com.cestec.cestec.repository.opr.agendamentosFuncRepo;
import com.cestec.cestec.repository.spf.notificacaoUsuRepository;
import com.cestec.cestec.service.opr.opr001s;
import com.cestec.cestec.util.utilForm;

import jakarta.transaction.Transactional;

@Service
public class comWindowService {
    @Autowired
    private sp_userService sp_user;

    @Autowired
    private metaRepository metaRepository;

    @Autowired
    private notificacaoUsuRepository notificRepository;

    @Autowired
    private contratoRepository contratoRepository;

    @Autowired
    private aplicacoesRepository aplicacoesRepository;

    @Autowired
    private agendamentosFuncRepo agendFuncRepo;

    @Autowired
    private modulosRepository modulosRepository;

    @Autowired
    private funcionarioRepository funcionarioRepository;

    @Autowired
    private histAcessAplRepo historicoAcessoAplRepo;

    public String getCorMotivo(Integer codmotivo){
        switch (codmotivo) {
        case 1: return "#96DF9C";
        case 2: return "#F28BF7";
        case 3: return "#ED8975";
        }
        return "#FFF";
    }

    public Double getMetaCorretorMensal(String ideusu){
        List<corretorDTO> corretores = metaRepository.findMetaMensalByIdeusu(ideusu);
        corretorDTO corretor = null;

        for(int i = 0; i < corretores.size(); i++ ){
            if(corretores.get(i).getDatiniciometa().before(Date.valueOf(LocalDate.now())) && 
               corretores.get(i).getDatfinalmeta().after(Date.valueOf(LocalDate.now()))){               
                corretor = corretores.get(i);
            }            
        }

        if (corretor != null) {
            return corretor.getVlrmeta();
        }
        else{
            return 0.0;
        }
    }

    public List<agendamentoDTO> buscarAgendamentosFunc(String ideusu){
        List<opr_agendamentos_func> agendfunc = agendFuncRepo.findAllByCodFunc(ideusu);
        List<agendamentoDTO> listaagendDTO    = new java.util.ArrayList<>();

        for(int i = 0; i < agendfunc.size(); i++){
            listaagendDTO.add(new agendamentoDTO(agendfunc.get(i).getCodfunc().getCodfuncionario(), agendfunc.get(i).getCodagenda().getMotivo(), agendfunc.get(i).getCodagenda().getId(), agendfunc.get(i).getCodagenda().getTitulo(), agendfunc.get(i).getCodagenda().getDescricao(), getCorMotivo(agendfunc.get(i).getCodagenda().getMotivo()), agendfunc.get(i).getCodagenda().getDatagen(), agendfunc.get(i).getCodagenda().getHoragen(), agendfunc.get(i).getIdeusu(), opr001s.getMotivo(agendfunc.get(i).getCodagenda().getMotivo())));
        }

         return listaagendDTO;
    }

    public List<historicoAcessoAplDTO> buscarHistoricoAcessoApl(String ideusu){
        return historicoAcessoAplRepo.findAllHistAcessAplByIdeusu(ideusu);
    }

    @Transactional
    public ResponseEntity<?> salvarHistoricoApl(String ideusu, Integer codmod, Integer codapl){
        try {
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            sp_histacessapl histAnalise = historicoAcessoAplRepo.findByAplicacao(ideusu, codmod, codapl);
            if (histAnalise == null) histAnalise = new sp_histacessapl();

            if(histAnalise.getId() == null || histAnalise.getId() == 0){
                sp_modulos modulo = modulosRepository.findByIdModulos(codmod);
                sp_aplicacoes apl = aplicacoesRepository.findByIdApl(codapl);

                Integer codfunc = funcionarioRepository.findCodFuncByIdeusu(ideusu);
                funcionario funcionario = funcionarioRepository.findFuncBycodfunc(codfunc);

                histAnalise.setIdmodulos(modulo);
                histAnalise.setIdaplicacao(apl);
                histAnalise.setIdfunc(funcionario);
                histAnalise.getDatregistro();
                histAnalise.setDatregistro(LocalDate.now());
                histAnalise.setIdeusu(ideusu);
            }

            Integer numacess = histAnalise.getNumacess() == null?0:histAnalise.getNumacess();
            histAnalise.setNumacess(numacess + 1);
            histAnalise.setAtualizado_em(LocalDate.now());

            historicoAcessoAplRepo.save(histAnalise);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage()); 
        }
    }

    public String getCargoFuncionario(String ideusu){
        Integer codfunc = funcionarioRepository.findCodFuncByIdeusu(ideusu);

        return funcionarioRepository.findCargoIdByNome(codfunc);
    }

    public Double getVlrEfetivadoCorretor(String ideusu){
        List<Double> valoresContrato = contratoRepository.buscarValoresContratoByIdeusu(ideusu);
        Double vlr = 0.0;

        for(int i = 0; i < valoresContrato.size(); i++){
            vlr += valoresContrato.get(i);
        }

        return vlr;
    }

    public Double getPercentMetaMes(String ideusu){
        Double meta = getMetaCorretorMensal(ideusu);        
        Double vlrEfetivado = getVlrEfetivadoCorretor(ideusu);

        return Math.floor((vlrEfetivado/ meta) * 100);
    }

    public String findMesMetaByIdeusu(String ideusu){
        pcp_meta meta  = metaRepository.findMesMetaByIdeusu(ideusu);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String periodo = sdf.format(meta.getDatinicio()).toString() + " - " + sdf.format(meta.getDatfinal()).toString();

        return periodo;
    }

    public Map<Integer, List<sp_aplicacoes>> getAplicacoesAgrupadasPorModulo() {
        List<sp_aplicacoes> todasAplicacoes = aplicacoesRepository.findAll();
        
        return todasAplicacoes.stream()
            .collect(Collectors.groupingBy(
                aplicacao -> aplicacao.getModulo().getId()
            ));
    }

    public List<?> buscarNotificacoesGrid(String ideusu){
        List<sp_notificacao_usu> notificacoes = notificRepository.findAllByIdeusu(ideusu);

        utilForm.initGrid();
        for (int i = 0; i < notificacoes.size(); i++) {
            String complemento = notificacoes.get(i).isAtivo()?"<span style='position: absolute; top: 4px; right:0; width: 10px; height: 10px; background-color: #cf0303; display: block; border-radius: 10px;'></span>":"";
            Date dataReg       =  Date.valueOf(notificacoes.get(i).getDatregistro());

            utilForm.criarRow();
            utilForm.criarColuna(notificacoes.get(i).getDescricao());
            utilForm.criarColuna("<div style='position: relative;'>" + utilForm.formatarDataBrasil(dataReg) + complemento + "</div>");
            utilForm.criarColuna(String.valueOf(notificacoes.get(i).isAtivo()));
            utilForm.criarColuna(notificacoes.get(i).getId().toString());
        }

        return utilForm.criarGrid();
    }

    @Transactional
    public ResponseEntity<?> inativarNotificacao(String ideusu, Integer idnotifi){
        try {
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.badRequest().body("Usuário não encontrado no sistema!");

            sp_notificacao_usu notificacao = notificRepository.findById(idnotifi).orElseThrow(() -> new RuntimeException("Notificação não encontrada 1"));
            if(notificacao.getId() == null || notificacao.getId() == 0){
                return ResponseEntity.badRequest().body("Notificação não encontrada 2");
            }

            if(!notificacao.isAtivo()) return ResponseEntity.ok("OK");

            notificacao.setAtivo(false);

            notificRepository.save(notificacao);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao desmarcar a notificação: " + e.getMessage()); 
        }
    }
}