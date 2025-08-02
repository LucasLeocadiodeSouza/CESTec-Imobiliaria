package com.cestec.cestec.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.spf.sp_notificacao_usu;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.generico.roleacessRepository;
import com.cestec.cestec.repository.spf.notificacaoUsuRepository;

@Service
public class sp_notificacaoService {
    @Autowired
    private notificacaoUsuRepository notificacaoUsuRepository;

    @Autowired
    private sp_userService sp_user;

    @Autowired
    private funcionarioRepository funcionarioRepo;
    
    @Autowired
    private roleacessRepository roleacess;

    /* ****** Funcoes ****** */

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> criarNotificacao(String ideusuFunc, String descricao, String ideusu){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.ok("Usuário não encontrado no sistema!");

            sp_notificacao_usu notificacaoAnalise = new sp_notificacao_usu();

            if(descricao.isEmpty() || descricao.isBlank()) return ResponseEntity.badRequest().body("Deve ser informado uma descricão!");

            funcionario funcionario = funcionarioRepo.findFuncByIdeusu(ideusuFunc);
            if(funcionario == null) return ResponseEntity.badRequest().body("Não foi possível vincular a notificação: usuário inexistente ou não registrado.");

            notificacaoAnalise.setFunc(funcionario);
            notificacaoAnalise.setDescricao(descricao);
            notificacaoAnalise.setDatregistro(LocalDate.now());
            notificacaoAnalise.setIdeusu(ideusu);
            notificacaoAnalise.setAtivo(true);

            notificacaoUsuRepository.save(notificacaoAnalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar uma notificacão: " + e.getMessage());    
        }
    }
}
