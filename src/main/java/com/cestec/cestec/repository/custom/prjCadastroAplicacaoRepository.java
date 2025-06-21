package com.cestec.cestec.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.sp_aplicacoes;
import jakarta.persistence.EntityManager;

@Repository
public class prjCadastroAplicacaoRepository {
    private final EntityManager em;

    public prjCadastroAplicacaoRepository(EntityManager em){
        this.em = em;
    }

    public List<sp_aplicacoes> buscarAplicacoes(Integer codapl, Integer codmodel, String ideusu){
        String query     = "SELECT apl FROM sp_aplicacoes apl WHERE apl.ideusu = :ideusu ORDER BY apl.modulo.id";

        if(codapl   != 0 && codapl   != null) query += " AND apl.id = :codapl ";
        if(codmodel != 0 && codmodel != null) query += " AND apl.modulo.id = :codmodel";

        var q = em.createQuery(query, sp_aplicacoes.class);
        q.setParameter("ideusu", ideusu);
        
        if(codapl != null && codapl != 0){
            q.setParameter("codapl", codapl);
        }
        if(codmodel != null && codmodel != 0){
            q.setParameter("codmodel", codmodel);
        }

        return q.getResultList();
    }
}
