package com.cestec.cestec.repository.custom;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.funcionarioDTO;
import jakarta.persistence.EntityManager;

@Repository
public class prjControleDeUsuarioRepository {
    private final EntityManager em;

    public prjControleDeUsuarioRepository(EntityManager em){
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
            q.setParameter("nome", nome);
        }

        return q.getResultList();
    }
}
