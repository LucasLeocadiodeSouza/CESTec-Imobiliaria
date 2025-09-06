package com.cestec.cestec.repository.cri;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.cri.pcp_contrato;
import jakarta.persistence.EntityManager;

@Repository
public class contratosCustomRepo {
    private final EntityManager em;

    public contratosCustomRepo(EntityManager em){
        this.em = em;
    }

    @Autowired
    private contratoRepository contratoRepo;

    public List<ImovelProprietarioDTO> buscarContratoAprovacao(Integer codcontrato, Integer codprop, Integer tipimovel){
        String query = "SELECT new com.cestec.cestec.model.ImovelProprietarioDTO( " +
                       "i.codimovel, i.pcp_proprietario.codproprietario, i.pcp_proprietario.nome, i.tipo, i.status, i.preco, i.negociacao, i.endereco, i.area, i.quartos, i.vlrcondominio, i.datinicontrato) " +
                       "FROM pcp_imovel i ";

        boolean temand = false;

        if(codcontrato != null && codcontrato != 0){
            List<pcp_contrato> contratos = contratoRepo.buscarContratosById(codcontrato);

            if(contratos.size() > 0) query += temand?" AND ":" WHERE ";
            if(contratos != null) {
                for(int i = 0; i<contratos.size(); i++){
                    query += (i > 0?" OR ":"") + " i.codimovel = " + contratos.get(i).getId().getCodimovel();
                }
                
                temand = true;
            }
        }

        if(codprop != null && codprop != 0){
            query += (temand?" AND ":" WHERE ") + " i.pcp_proprietario.codproprietario = :codpropri";
            temand = true;
        }

        if(tipimovel != 0){
            query += (temand?" AND ":" WHERE ") + " i.negociacao = :tipoimovel";
            temand = true;
        }

        var q = em.createQuery(query, ImovelProprietarioDTO.class);

        if(codprop != null && codprop != 0) q.setParameter("codpropri", codprop);

        if(tipimovel != null && tipimovel != 0) q.setParameter("tipoimovel", tipimovel);

        return q.getResultList();
    }
}
