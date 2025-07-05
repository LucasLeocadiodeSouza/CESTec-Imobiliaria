package com.cestec.cestec.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.corretorDTO;
import com.cestec.cestec.model.pcp_meta;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.model.opr.agendamentoDTO;
import com.cestec.cestec.model.opr.opr_agendamentos_func;
import com.cestec.cestec.repository.contratoRepository;
import com.cestec.cestec.repository.metaRepository;
import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.opr.agendamentosFuncRepo;

@Service
public class comWindowService {

    @Autowired
    private metaRepository metaRepository;

    @Autowired
    private contratoRepository contratoRepository;

    @Autowired
    private aplicacoesRepository aplicacoesRepository;

    @Autowired
    private agendamentosFuncRepo agendFuncRepo;

    @Autowired
    private funcionarioRepository funcionarioRepository;

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
            listaagendDTO.add(new agendamentoDTO(agendfunc.get(i).getCodfunc().getCodfuncionario(), agendfunc.get(i).getCodagenda().getMotivo(), agendfunc.get(i).getCodagenda().getId(), getCorMotivo(agendfunc.get(i).getCodagenda().getMotivo()), agendfunc.get(i).getCodagenda().getDatagen(), agendfunc.get(i).getCodagenda().getHoragen()));
        }

         return listaagendDTO;
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
}
