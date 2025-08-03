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
            q.setParameter("ideusu", ideusu);
        }

        return q.getResultList();
    }
}
