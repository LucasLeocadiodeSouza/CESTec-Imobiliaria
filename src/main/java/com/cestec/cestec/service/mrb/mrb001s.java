package com.cestec.cestec.service.mrb;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.aplicacaoDTO;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.model.sp_modulos;
import com.cestec.cestec.model.sp_roleacess;
import com.cestec.cestec.repository.custom.prjCadastroAplicacaoRepository;
import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.modulosRepository;
import com.cestec.cestec.repository.generico.roleacessRepository;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class mrb001s {

    @Autowired
    private prjCadastroAplicacaoRepository cadasaplcustomrepo;

    @Autowired
    private modulosRepository modulosRepository;

    @Autowired
    private aplicacoesRepository aplicacoesRepository;

    @Autowired
    private sp_userService sp_user;
    
    @Autowired
    private roleacessRepository roleacess;

    public List<?> buscarAplicacoesGrid(Integer codapl, Integer codmodu, String ideusu){
        List<sp_aplicacoes> aplicacao = cadasaplcustomrepo.buscarAplicacoes(codapl,codmodu, ideusu);

        utilForm.initGrid();
        for (int i = 0; i < aplicacao.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(aplicacao.get(i).getIdeusu());
            utilForm.criarColuna(aplicacao.get(i).getModulo().getId().toString());
            utilForm.criarColuna(aplicacao.get(i).getModulo().getDescricao());
            utilForm.criarColuna(aplicacao.get(i).getId().toString());
            utilForm.criarColuna(aplicacao.get(i).getDescricao());
            utilForm.criarColuna(aplicacao.get(i).getDatregistro().toString());
            utilForm.criarColuna(aplicacao.get(i).getRole().getId().toString());
            utilForm.criarColuna(aplicacao.get(i).getArquivo_inic());
        }

        return utilForm.criarGrid();
    }

    public List<aplicacaoDTO> buscarAllRoles(){
        return roleacess.findAllRole();
    }

    public String getDescricaoModulo(Integer codmodulo){
        return modulosRepository.findByIdModulos(codmodulo).getDescricao();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> cadastrarAplicacao(aplicacaoDTO aplicacao){
        try{
            if(sp_user.loadUserByUsername(aplicacao.getIdeusu()) == null) return ResponseEntity.ok("Usuário não encontrado no sistema!");

            sp_aplicacoes aplicacaoAnalise = aplicacoesRepository.findByIdApl(aplicacao.getId());
            if (aplicacaoAnalise == null) aplicacaoAnalise = new sp_aplicacoes();

            if(aplicacao.getDescricao() == null && aplicacao.getDescricao() == "") return ResponseEntity.badRequest().body("Aplicacão deve conter uma descricão!");

            if(aplicacao.getArquivo_inic() == null && aplicacao.getArquivo_inic() == "") return ResponseEntity.badRequest().body("Deve ser informado um arquivo de inicializacão para a aplicacão!");

            if(!modulosRepository.existsById(aplicacao.getIdmodulo())) return ResponseEntity.badRequest().body("Modulo da aplicacão não encontrado!");
            sp_modulos modulo = modulosRepository.findByIdModulos(aplicacao.getIdmodulo());
            aplicacaoAnalise.setModulo(modulo);

            aplicacaoAnalise.setDescricao(aplicacao.getDescricao());
            aplicacaoAnalise.setArquivo_inic(aplicacao.getArquivo_inic());

            if(!roleacess.existsById(aplicacao.getRole())) return ResponseEntity.badRequest().body("Role da aplicacão não encontrado!");
            sp_roleacess role = roleacess.findByCodRole(aplicacao.getRole());
            aplicacaoAnalise.setRole(role);

            if(aplicacao.getId() == null || aplicacao.getId() == 0){
                aplicacaoAnalise.setDatregistro(LocalDate.now());

                aplicacaoAnalise.setIdeusu(aplicacao.getIdeusu());
            }

            aplicacoesRepository.save(aplicacaoAnalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar aplicacão: " + e.getMessage());    
        }
    }
}