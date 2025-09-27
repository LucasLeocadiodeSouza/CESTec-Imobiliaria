package com.cestec.cestec.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.model.historicoAcessoAplDTO;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.model.opr.agendamentoDTO;
import com.cestec.cestec.model.spf.sp_usu_aplfav;
import com.cestec.cestec.service.comWindowService;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.sp_userService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/home") // URL base para acessar
public class comWindowController {

    @Autowired
    private genService gen;

    @Autowired
    private sp_userService sp_userService;

    @Autowired
    private tokenService tokenService;

    @Autowired
    private comWindowService comWindowService;

    @GetMapping("/userlogin")
    public String getUserName(HttpServletRequest request) {
        return gen.getUserName(request);
    }

    @GetMapping("/getBotoesAplMenu")
    public Map<Integer, List<sp_aplicacoes>> getBotoesAplMenu() {
        return comWindowService.getAplicacoesAgrupadasPorModulo();
    }

    @GetMapping("/userid")
    public Integer getUserId(HttpServletRequest request) {
        return sp_userService.getIdByUserName(getUserName(request));        
    }

    @GetMapping("/{ideusu}/valorMetaMensal")
    public BigDecimal valorMetaMensal(@PathVariable String ideusu) {
        return comWindowService.getMetaCorretorMensal(ideusu);
    }
    
    @GetMapping("/getVlrEfetivadoCorretor")
    public BigDecimal getVlrEfetivadoCorretor(HttpServletRequest request) {
        return comWindowService.getVlrEfetivadoCorretor(getUserName(request));
    }

    @GetMapping("/buscarAgendamentosFunc")
    public List<agendamentoDTO> buscarAgendamentosFunc(String ideusu){
        return comWindowService.buscarAgendamentosFunc(ideusu);
    }

    @GetMapping("/buscarAplicacoesFav")
    public List<sp_usu_aplfav> buscarAplicacoesFav(HttpServletRequest request){
        return comWindowService.buscarAplicacoesFav(getUserName(request));
    }

    @GetMapping("/ehAplicacaoFavUsu")
    public Boolean ehAplicacaoFavUsu(HttpServletRequest request, @RequestParam(value = "codapl", required = false) Integer codapl){
        return comWindowService.ehAplicacaoFavUsu(getUserName(request),codapl);
    }

    @PostMapping("/inserirDeletarAplicacaoFav")
    public ResponseEntity<?> inserirDeletarAplicacaoFav(HttpServletRequest request, @RequestParam(value = "codapl", required = false) Integer codapl){
        try {
            String ideusu = gen.getUserName(request);

            comWindowService.inserirDeletarAplicacaoFav(ideusu, codapl);
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/getPeriodoMeta")
    public String getPeriodoMeta(HttpServletRequest request) {
        return comWindowService.findMesMetaByIdeusu(getUserName(request));
    }

    @GetMapping("/getPercentMetaMes")
    public BigDecimal getPercentMetaMes(HttpServletRequest request) {
        return comWindowService.getPercentMetaMes(getUserName(request));
    }
    
    @GetMapping("/getCargoIdeusu")
    public String getCargoIdeusu(HttpServletRequest request) {
        return comWindowService.getCargoFuncionario(getUserName(request));
    }

    @GetMapping("/buscarHistoricoAcessoApl")
    public List<historicoAcessoAplDTO> buscarHistoricoAcessoApl(HttpServletRequest request) {
        return comWindowService.buscarHistoricoAcessoApl(getUserName(request));
    }

    @PostMapping("/salvarHistoricoApl")
    public ResponseEntity<?> salvarHistoricoApl(HttpServletRequest request, @RequestParam(value = "codmod", required = false) Integer codmod, @RequestParam(value = "codapl", required = false) Integer codapl) {
        try {
            comWindowService.salvarHistoricoApl(getUserName(request), codmod, codapl);
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar chamados cadastrados: " + e.getMessage());
        }
    } 

    @GetMapping("/buscarNotificacoesGrid")
    public List<?> buscarNotificacoesGrid(HttpServletRequest request) {
        return comWindowService.buscarNotificacoesGrid(getUserName(request));
    }

    @PostMapping("/inativarNotificacao")
    public ResponseEntity<?> inativarNotificacao(HttpServletRequest request, @RequestParam(value = "idnotific", required = false) Integer idnotific) {
        try {
            comWindowService.inativarNotificacao(getUserName(request), idnotific);
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar chamados cadastrados: " + e.getMessage());
        }
    }

    @GetMapping("/findAllChamadosByIdeusu")
    public ResponseEntity<?> findAllChamadosByIdeusu(HttpServletRequest request){
        try {
            String ideusu = gen.getUserName(request);

            return ResponseEntity.ok().body(comWindowService.findAllChamadosByIdeusu(ideusu));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar chamados cadastrados: " + e.getMessage());
        }
    }
}