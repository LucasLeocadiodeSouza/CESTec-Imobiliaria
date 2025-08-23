package com.cestec.cestec.repository.opr;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.chamadoSolicDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class opr002Repo {
    private final EntityManager em;

    @PersistenceContext
    private EntityManager em2;

    public opr002Repo(EntityManager em){
        this.em = em;
    }

    
    public List<chamadoSolicDTO> buscarSolicitacoes(String ideusu, boolean somenteAtivo){
        String query   = "SELECT new com.cestec.cestec.model.opr.chamadoSolicDTO(" + 
                         "solic.id, func.nome, solic.titulo, solic.descricao, solic.estado, solic.prioridade, solic.complex, solic.previsao, solic.obs, solic.datconcl, solic.feedback, solic.datregistro, solic.ideususolic)" + 
                         " FROM opr_chamados_solic solic " +
                         " JOIN solic.codfuncfila func";

        boolean temand = false;

        if(ideusu != null){
            query += " WHERE solic.ideususolic LIKE :ideusu ";
            temand = true;
        }
        if(somenteAtivo){
            query += (temand?" AND ":" WHERE ") + " solic.estado = 1 OR solic.estado = 2 ";
            temand = true;
        }  


        var q = em.createQuery(query, chamadoSolicDTO.class);

        if(ideusu != null){
            q.setParameter("ideusu", ideusu);
        }

        return q.getResultList();
    }

    public List<chamadoSolicDTO> buscarChamados(String ideusu, boolean somenteAtivo, String acao){
        String query   = "SELECT new com.cestec.cestec.model.opr.chamadoSolicDTO(" + 
                         "solic.id, funcfila.nome, solic.titulo, solic.descricao, solic.estado, solic.prioridade, solic.complex, solic.previsao, solic.obs, solic.datconcl, solic.feedback, solic.datregistro, solic.ideususolic)" + 
                         " FROM opr_chamados_solic solic " +
                         " JOIN solic.codfuncfila funcfila" +
                         " JOIN funcfila.sp_user user" +
                         " WHERE solic.estado = 1";
        boolean temand = false;

        if(ideusu != null){
            query += " AND user.login LIKE :ideusu ";
            temand = true;
        }
        if(somenteAtivo){
            query += " AND  solic.estado = 3 ";
            temand = true;
        }  
        if(acao.equals("direcionar")){
            query += " AND  user.login LIKE 'FILA' ";
            temand = true;
        }

        var q = em.createQuery(query, chamadoSolicDTO.class);

        if(ideusu != null){
            q.setParameter("ideusu", ideusu);
        }

        return q.getResultList();
    }
}
