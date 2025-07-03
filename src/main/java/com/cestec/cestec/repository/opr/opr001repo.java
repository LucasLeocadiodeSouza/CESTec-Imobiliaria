package com.cestec.cestec.repository.opr;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.funcionarioDTO;
import com.cestec.cestec.model.opr.agendamentoDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class opr001repo {
    private final EntityManager em;

    @PersistenceContext
    private EntityManager em2;

    public opr001repo(EntityManager em){
        this.em = em;
    }

    public List<funcionarioDTO> buscarFuncionarios(String nome){
        String query   = "SELECT new com.cestec.cestec.model.funcionarioDTO(" + 
                         "func.codfuncionario, func.nome, func.cpf, func.numtel, set.codsetor, set.nome, car.id, car.nome)" + 
                         " FROM funcionario func " + 
                         " JOIN func.setor set "  + 
                         " JOIN func.cargo car ";
        boolean temand = false;

        if(nome != null){
            query += (temand?" AND ":" WHERE ") + " func.nome LIKE :nome ";
            temand = true;
        } 

        var q = em.createQuery(query, funcionarioDTO.class);

        if(nome != null){
            q.setParameter("nome", nome + "%");
        }

        return q.getResultList();
    }

    public List<agendamentoDTO> buscarAgendamentos(Integer codfunc, Integer codcargo, Integer codsetor){
        String query   = "SELECT new com.cestec.cestec.model.opr.agendamentoDTO(" + 
                         "agend.id, agend.titulo, agend.descricao, agend.motivo, agend.datagen, agend.horagen, agend.ideusu)" + 
                         " FROM opr_agendamentos agend ";
        // boolean temand = false;

        // if(codcargo != 0 || codcargo   != null) {
        //     query += (temand?" AND ":" WHERE ") + " cargo.id = :codcargo ";
        //     temand = true;
        // }
        // if(codsetor != 0 || codsetor != null){
        //     query += (temand?" AND ":" WHERE ") + " set.codsetor = :codsetor ";
        //     temand = true;
        // } 
        // if(codfunc != 0 || codfunc != null){
        //     query += (temand?" AND ":" WHERE ") + " func.codfuncionario = :codfunc ";
        //     temand = true;
        // } 

        var q = em.createQuery(query, agendamentoDTO.class);
        
        // if(codcargo != null && codcargo != 0 ){
        //    q.setParameter("codcargo", codcargo);
        // }
        // if(codsetor != null && codsetor != 0){
        //    q.setParameter("codsetor", codsetor);
        // }
        // if(codfunc != null && codfunc != 0){
        //     q.setParameter("codfunc", codfunc);
        // }

        return q.getResultList();
    }
}
