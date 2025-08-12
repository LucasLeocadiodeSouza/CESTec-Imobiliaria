package com.cestec.cestec.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.sp_aplicacoes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class zoomRepository {
    private final EntityManager em;
    private static String resultClass;

    @PersistenceContext
    private EntityManager em2;

    public zoomRepository(EntityManager em){
        this.em = em;
    }

    public List<?> carregarQuery(String query, List<modelUtilForm> parametros) throws ClassNotFoundException{
        Class<?> clazz = Class.forName(resultClass);

        var q = em.createQuery(query, clazz);

        for(int i = 0; i < parametros.size(); i++) {
            q.setParameter(parametros.get(i).getParamName(), parametros.get(i).getDescricao());
        }

        return q.getResultList();
    }
}
