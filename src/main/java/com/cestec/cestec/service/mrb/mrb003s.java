package com.cestec.cestec.service.mrb;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cestec.cestec.model.cargo;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.funcionarioDTO;
import com.cestec.cestec.model.pcp_setor;
import com.cestec.cestec.repository.cargoRepository;
import com.cestec.cestec.repository.setorRepository;
import com.cestec.cestec.repository.custom.prjControleDeUsuarioRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.service.sp_userService;
import com.cestec.cestec.util.utilForm;

@Service
public class mrb003s {
    @Autowired
    private funcionarioRepository funcionarioRepo;

    @Autowired
    private prjControleDeUsuarioRepository prjControleDeUsuarioRepository;

    @Autowired
    private sp_userService sp_user;

    @Autowired
    private setorRepository setorRepository;

    @Autowired
    private cargoRepository cargoRepository;

    public String buscarNomeCargo(Integer codcargo){
        cargo cargoAnalise = cargoRepository.findCargoByCodCargo(codcargo);
        return cargoAnalise.getNome();
    }

    public String buscarNomeSetor(Integer codsetor){
        pcp_setor setor = setorRepository.findSetorByCodSetor(codsetor);
        return setor.getNome();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> cadastrarUsuario(funcionarioDTO funcionario, String ideusu, String acao){
        try{
            if(sp_user.loadUserByUsername(ideusu) == null) return ResponseEntity.ok("Usuário não encontrado no sistema!");

            if(acao == "Inserindo" && 
                acao == "Alterando") return ResponseEntity.badRequest().body("Ação informada invalida!");

            if(funcionario.getNomefunc() == null && funcionario.getNomefunc() == "") return ResponseEntity.badRequest().body("Deve ser informado o Nome do Usuario!");

            if(funcionario.getCpf() == null && funcionario.getCpf() == "") return ResponseEntity.badRequest().body("Deve ser informado o numero do Documento 'cpf' do usuário!");
            if(funcionario.getCpf().length() != 11) return ResponseEntity.badRequest().body("Deve ser informado o numero do Documento 'cpf' do usuário!");

            if(funcionario.getEndereco() == null && funcionario.getEndereco() == "") return ResponseEntity.badRequest().body("Deve ser informado o endereço do usuário!");

            if(funcionario.getDatinasc() == null) return ResponseEntity.badRequest().body("Deve ser informado a data de nascimento do usuário!");

            if(funcionario.getCodsetor() == null) return ResponseEntity.badRequest().body("Deve ser informado o Setor do usuário!");
            pcp_setor setorfunc = setorRepository.findSetorByCodSetor(funcionario.getCodsetor());
            if(setorfunc == null) return ResponseEntity.badRequest().body("Setor informado não cadastrado!");

            if(funcionario.getCodcargo() == null) return ResponseEntity.badRequest().body("Deve ser informado o Cargo do usuário!");
            cargo cargofunc = cargoRepository.findCargoByCodCargo(funcionario.getCodcargo());
            if(cargofunc == null) return ResponseEntity.badRequest().body("Cargo informado não cadastrado!");

            if(funcionario.getSalario() == 0) return ResponseEntity.badRequest().body("Deve ser informado o Salario do usuário!");

            funcionario funcAnalise = funcionarioRepo.findFuncBycodfunc(funcionario.getCodfunc());

            if(acao == "Inserindo"){
                if(funcAnalise != null) return ResponseEntity.badRequest().body("Usuário [" + funcionario.getNomefunc() + "] já está cadastrado no sistema!");

                funcAnalise = new funcionario();
                funcAnalise.setIdeusu(ideusu);
                funcAnalise.setCriado_em(LocalDate.now());
            }
            else if(acao == "Alterando"){
                if(funcAnalise == null) return ResponseEntity.badRequest().body("Usuário [" + funcionario.getNomefunc() + "] não encontrado no sistema sistema!");
            }

            funcAnalise.setNome(funcionario.getNomefunc());
            funcAnalise.setCpf(funcionario.getCpf());
            funcAnalise.setEndereco(funcionario.getEndereco());
            funcAnalise.setDatinasc(funcionario.getDatinasc());
            funcAnalise.setSetor(setorfunc);
            funcAnalise.setCargo(cargofunc);
            funcAnalise.setSalario(funcionario.getSalario());

            funcionarioRepo.save(funcAnalise);
            return ResponseEntity.ok("OK");
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar o Usuário: " + e.getMessage());    
        }
    }


    /* Grids */
    public List<?> carregarGridFuncionarios(String nomefunc){
        List<funcionarioDTO> func = prjControleDeUsuarioRepository.buscarFuncionarios(nomefunc);
        
        
        utilForm.initGrid();
        for (int i = 0; i < func.size(); i++) {
            Date dataCriacao = Date.valueOf(func.get(i).getCriado_em());

            utilForm.criarRow();
            utilForm.criarColuna(func.get(i).getCodfunc().toString());
            utilForm.criarColuna(func.get(i).getNomefunc());
            utilForm.criarColuna(func.get(i).getCodsetor().toString());
            utilForm.criarColuna(func.get(i).getNomesetor());
            utilForm.criarColuna(func.get(i).getCodcargo().toString());
            utilForm.criarColuna(func.get(i).getNomecargo());
            utilForm.criarColuna(utilForm.formatarDataBrasil(dataCriacao));
            utilForm.criarColuna(func.get(i).getDatinasc().toString());
            utilForm.criarColuna(func.get(i).getCpf());
            utilForm.criarColuna(func.get(i).getEndereco());
            utilForm.criarColuna(String.valueOf(func.get(i).getSalario()));
            utilForm.criarColuna(func.get(i).getCriado_em().toString());
        }

        return utilForm.criarGrid();
    }
}
