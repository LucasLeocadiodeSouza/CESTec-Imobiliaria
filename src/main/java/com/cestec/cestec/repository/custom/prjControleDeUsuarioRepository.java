package com.cestec.cestec.repository.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.funcionarioDTO;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.model.sp_modulos;
import com.cestec.cestec.model.opr.opr_versionamento;
import com.cestec.cestec.model.opr.opr_versionamentoId;
import com.cestec.cestec.model.spf.sp_bloqueia_acess;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_resp;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_respId;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_usu;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_usuId;
import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.generico.modulosRepository;

import jakarta.persistence.EntityManager;

@Repository
public class prjControleDeUsuarioRepository {
    private final EntityManager em;

    @Autowired
    private aplicacoesRepository aplRepo;

    @Autowired
    private funcionarioRepository funcRepo;

    @Autowired
    private modulosRepository modRepo;

    public prjControleDeUsuarioRepository(EntityManager em){
        this.em = em;
    }

    public sp_bloqueia_acess_resp getBloqueioRespById(Integer idfunc, Integer idbloqacess) {
        sp_bloqueia_acess_respId bloqueioId = new sp_bloqueia_acess_respId(idfunc, idbloqacess);
        return em.find(sp_bloqueia_acess_resp.class, bloqueioId);
    }

    public sp_bloqueia_acess_usu getBloqueioUsuById(Integer idfunc, Integer idbloqacess) {
        sp_bloqueia_acess_usuId bloqueioId = new sp_bloqueia_acess_usuId(idfunc, idbloqacess);
        return em.find(sp_bloqueia_acess_usu.class, bloqueioId);
    }

    public List<funcionarioDTO> buscarFuncionarios(String ideusu){
        String query   = "SELECT new com.cestec.cestec.model.funcionarioDTO(" + 
                         "func.codfuncionario, func.nome, func.cpf, func.numtel, set.codsetor, set.nome, car.id, car.nome, func.endereco, func.salario, func.datinasc, func.criado_em) " + 
                         "FROM funcionario func " + 
                         "JOIN func.setor set "  + 
                         "JOIN func.cargo car ";
        boolean temand = false;
        
        if(ideusu != null && !ideusu.isEmpty()){
            query += (temand?" AND ":" WHERE ") + " func.nome LIKE :ideusu ";
            temand = true;
        } 

        var q = em.createQuery(query, funcionarioDTO.class);

        if(ideusu != null && !ideusu.isEmpty()){
            q.setParameter("ideusu", ideusu + "%");
        }

        return q.getResultList();
    }

    public List<sp_bloqueia_acess> buscarBloqueios(Integer codapl, Integer codmod){
        String query   = "SELECT bloq FROM sp_bloqueia_acess bloq ";
        boolean temand = false;
        
        if(codapl != null && codapl != 0){
            query += " WHERE bloq.aplicacao = :aplica ";
            temand = true;
        } 

        if(codmod != null && codmod != 0){
            query += (temand?" AND ":" WHERE ") + " bloq.modulo = :modulo ";
            temand = true;
        } 

        var q = em.createQuery(query, sp_bloqueia_acess.class);

        if(codapl != null && codapl != 0){
            sp_aplicacoes aplica = aplRepo.findByCodApl(codapl);

            q.setParameter("aplica", aplica);
        }

        if(codmod != null && codmod != 0){
            sp_modulos modulo = modRepo.findByIdModulos(codmod);

            q.setParameter("modulo", modulo);
        }

        return q.getResultList();
    }
}
