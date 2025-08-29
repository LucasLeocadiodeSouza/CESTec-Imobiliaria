package com.cestec.cestec.service.mrb;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.model.sp_modulos;
import com.cestec.cestec.model.spf.sp_bloqueia_acess;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_resp;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_respId;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_usu;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_usuId;
import com.cestec.cestec.repository.custom.prjControleDeUsuarioRepository;
import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.generico.modulosRepository;
import com.cestec.cestec.repository.spf.bloqueioAcessRepo;
import com.cestec.cestec.repository.spf.bloqueioAcessRespRepo;
import com.cestec.cestec.repository.spf.bloqueioAcessUsuRepo;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class mrb004s {
    @Autowired
    private bloqueioAcessRepo bloqAcess;

    @Autowired
    private bloqueioAcessUsuRepo bloqAcessUsuRepo;

    @Autowired
    private aplicacoesRepository aplicacoesRepository;

    @Autowired
    private modulosRepository modulosRepository;

    @Autowired
    private prjControleDeUsuarioRepository prjControleDeUsuarioRepository;

    @Autowired
    private funcionarioRepository funcRepo;

    @Autowired
    private genService gen;

    @Autowired
    private sp_userService sp_user;

    @Autowired
    private bloqueioAcessRespRepo bloqAcessRespRepo;


    @Transactional
    public void cadastrarBloqueio(Integer codapl, Integer codmod, String ideusu){
        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");

        sp_modulos modulo = modulosRepository.findByIdModulos(codmod);
        if(modulo == null) throw new RuntimeException("Modulo da aplicacão não encontrado!");

        sp_aplicacoes aplicacao = aplicacoesRepository.findByIdApl(codapl);
        if(aplicacao == null) throw new RuntimeException("Aplicacão deve não encontrada com o codigo informado '" + codapl + "'!");

        if(!aplicacao.getModulo().equals(modulo)) throw new RuntimeException("O módulo informado '" + codmod + "' é diferente do módulo cadastrado para a Aplicação '" + codapl + "'!");

        sp_bloqueia_acess bloqueio = new sp_bloqueia_acess();
        bloqueio.setAplicacao(aplicacao);
        bloqueio.setModulo(modulo);
        bloqueio.setDatregistro(LocalDate.now());
        bloqueio.setIdeusu(ideusu);

        bloqAcess.save(bloqueio);

        funcionario funcionario = funcRepo.findFuncByIdeusu(ideusu);

        sp_bloqueia_acess_resp responsavel = new sp_bloqueia_acess_resp();
        responsavel.setAtivo(true);
        responsavel.setBloqueio_acess(bloqueio);
        responsavel.setDatregistro(LocalDate.now());
        responsavel.setFuncionario(funcionario);
        responsavel.setIdeusu(ideusu);
        responsavel.setSeq(new sp_bloqueia_acess_respId(funcionario.getCodfuncionario(), bloqueio.getId()));

        bloqAcessRespRepo.save(responsavel);
    }

    @Transactional
    public void cadastrarBloqueioResponsavel(String ideusuSolic, Integer codbloqueio, String ideusu){
        if(ideusuSolic == null || ideusuSolic.isBlank()) throw new RuntimeException("Informe um Usuário para incluir no bloqueio!");

        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");
        if(sp_user.loadUserByUsername(ideusuSolic) == null) throw new RuntimeException("Funcionario não encontrado no sistema!");

        if(!bloqAcess.existsById(codbloqueio)) throw new RuntimeException("Bloqueio deve não encontrado com o codigo informado '" + codbloqueio + "'!");
        sp_bloqueia_acess bloqueio = bloqAcess.findByIdBloq(codbloqueio);

        funcionario funcionario = funcRepo.findFuncByIdeusu(ideusuSolic);

        if(bloqAcessRespRepo.existsById(new sp_bloqueia_acess_respId(funcionario.getCodfuncionario(), codbloqueio))) throw new RuntimeException("Usuário já está registrado no bloqueio!");

        sp_bloqueia_acess_resp bloqueioResp = new sp_bloqueia_acess_resp();
        bloqueioResp.setBloqueio_acess(bloqueio);
        bloqueioResp.setFuncionario(funcionario);
        bloqueioResp.setAtivo(true);
        bloqueioResp.setDatregistro(LocalDate.now());
        bloqueioResp.setIdeusu(ideusu);
        bloqueioResp.setSeq(new sp_bloqueia_acess_respId(funcionario.getCodfuncionario(), codbloqueio));

        bloqAcessRespRepo.save(bloqueioResp);
    }

    @Transactional
    public void cadastrarBloqueioUsuario(String ideusuSolic, Integer codbloqueio, String ideusu){
        if(ideusuSolic == null || ideusuSolic.isBlank()) throw new RuntimeException("Informe um Usuário para incluir no bloqueio!");

        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");
        if(sp_user.loadUserByUsername(ideusuSolic) == null) throw new RuntimeException("Funcionario não encontrado no sistema!");

        if(!bloqAcess.existsById(codbloqueio)) throw new RuntimeException("Bloqueio deve não encontrado com o codigo informado '" + codbloqueio + "'!");
        sp_bloqueia_acess bloqueio = bloqAcess.findByIdBloq(codbloqueio);

        funcionario funcionario = funcRepo.findFuncByIdeusu(ideusuSolic);

        if(bloqAcessUsuRepo.existsById(new sp_bloqueia_acess_usuId(funcionario.getCodfuncionario(), codbloqueio))) throw new RuntimeException("Usuário já está registrado no bloqueio!");

        sp_bloqueia_acess_usu bloqueioUsu = new sp_bloqueia_acess_usu();
        bloqueioUsu.setBloqueio_acess(bloqueio);
        bloqueioUsu.setFuncionario(funcionario);
        bloqueioUsu.setAtivo(true);
        bloqueioUsu.setDatregistro(LocalDate.now());
        bloqueioUsu.setIdeusu(ideusu);
        bloqueioUsu.setSeq(new sp_bloqueia_acess_usuId(funcionario.getCodfuncionario(), codbloqueio));

        bloqAcessUsuRepo.save(bloqueioUsu);
    }

    @Transactional
    public void alteraEstadoBloqueioUsuario(String ideusuSolic, Integer codbloqueio, String ideusu){
        if(ideusuSolic == null || ideusuSolic.isBlank()) throw new RuntimeException("Informe um Usuário para Ativar/Inativar!");

        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");
        if(sp_user.loadUserByUsername(ideusuSolic) == null) throw new RuntimeException("Funcionario não encontrado no sistema!");

        if(!bloqAcess.existsById(codbloqueio)) throw new RuntimeException("Bloqueio deve não encontrado com o codigo informado '" + codbloqueio + "'!");
        sp_bloqueia_acess bloqueio = bloqAcess.findByIdBloq(codbloqueio);

        funcionario funcionario = funcRepo.findFuncByIdeusu(ideusuSolic);

        sp_bloqueia_acess_usu bloqueioUsu = prjControleDeUsuarioRepository.getBloqueioUsuById(funcionario.getCodfuncionario(), bloqueio.getId());
        if(bloqueioUsu == null) throw new RuntimeException("Bloqueio para usuario não encontrado no sistema!");

        bloqueioUsu.setAtivo(!bloqueioUsu.isAtivo());

        bloqAcessUsuRepo.save(bloqueioUsu);
    }

    @Transactional
    public void alteraEstadoBloqueioResp(String ideusuSolic, Integer codbloqueio, String ideusu){
        if(ideusuSolic == null || ideusuSolic.isBlank()) throw new RuntimeException("Informe um Usuário para Ativar/Inativar!");

        if(sp_user.loadUserByUsername(ideusu) == null) throw new RuntimeException("Usuário não encontrado no sistema!");
        if(sp_user.loadUserByUsername(ideusuSolic) == null) throw new RuntimeException("Funcionario não encontrado no sistema!");

        if(!bloqAcess.existsById(codbloqueio)) throw new RuntimeException("Bloqueio deve não encontrado com o codigo informado '" + codbloqueio + "'!");
        sp_bloqueia_acess bloqueio = bloqAcess.findByIdBloq(codbloqueio);

        funcionario funcionario = funcRepo.findFuncByIdeusu(ideusuSolic);
        
        sp_bloqueia_acess_resp bloqueioResp = prjControleDeUsuarioRepository.getBloqueioRespById(funcionario.getCodfuncionario(), bloqueio.getId());
        if(bloqueioResp == null) throw new RuntimeException("Bloqueio para usuario não encontrado no sistema!");

        bloqueioResp.setAtivo(!bloqueioResp.isAtivo());

        bloqAcessRespRepo.save(bloqueioResp);
    }

    public Boolean getTemUsuariosBloqueados(Integer idbloq){
        List<sp_bloqueia_acess_usu> usuariosBloq = bloqAcessUsuRepo.findAllBloqueioUsuAtivos(idbloq);

        return usuariosBloq.size() > 0;
    }

    /* Grids */
    public List<?> carregarGridBloqueios(Integer codapl, Integer codmod){
        List<sp_bloqueia_acess> bloq = prjControleDeUsuarioRepository.buscarBloqueios(codapl, codmod);
        
        utilForm.initGrid();
        for (int i = 0; i < bloq.size(); i++) {
            Date dataCriacao = Date.valueOf(bloq.get(i).getDatregistro());

            utilForm.criarRow();
            utilForm.criarColuna(bloq.get(i).getId().toString());
            utilForm.criarColuna(bloq.get(i).getModulo().getId().toString());
            utilForm.criarColuna(bloq.get(i).getModulo().getDescricao());
            utilForm.criarColuna(bloq.get(i).getAplicacao().getId().toString());
            utilForm.criarColuna(bloq.get(i).getAplicacao().getDescricao());
            utilForm.criarColuna(utilForm.formatarLogical(getTemUsuariosBloqueados(bloq.get(i).getId())));
            utilForm.criarColuna(bloq.get(i).getIdeusu());
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataCriacao));
        }

        return utilForm.criarGrid();
    }

    public List<?> carregarGridUsuariosBloq(Integer idbloq){
        List<sp_bloqueia_acess_usu> usubloq = bloqAcessUsuRepo.findAllBloqueioUsu(idbloq);
        
        utilForm.initGrid();
        for (int i = 0; i < usubloq.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(usubloq.get(i).getFuncionario().getSp_user().getLogin());
            utilForm.criarColuna(usubloq.get(i).getFuncionario().getNome());
            utilForm.criarColuna("<a id='ativarusu" + i + "' name='ativarusu" + i + "'>" + (usubloq.get(i).isAtivo()?"Inativar":"Ativar") + "</a>");
        }

        return utilForm.criarGrid();
    }

    public List<?> carregarGridUsuariosResp(Integer idbloq){
        List<sp_bloqueia_acess_resp> respbloq = bloqAcessRespRepo.findAllBloqueioResp(idbloq);
        
        utilForm.initGrid();
        for (int i = 0; i < respbloq.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(respbloq.get(i).getFuncionario().getSp_user().getLogin());
            utilForm.criarColuna(respbloq.get(i).getFuncionario().getNome());
            utilForm.criarColuna("<a id='ativarresp" + i + "' name='ativarresp" + i + "'>" + (respbloq.get(i).isAtivo()?"Inativar":"Ativar") + "</a>");
        }

        return utilForm.criarGrid();
    }
}
