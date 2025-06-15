package com.cestec.cestec.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.model.pcp_cliente;
import com.cestec.cestec.model.pcp_proprietario;

import jakarta.persistence.EntityManager;

@Repository
public class prjContratosCustomRepository {
    
    private final EntityManager em;

    public prjContratosCustomRepository(EntityManager em){
        this.em = em;
    }

    public List<contratoDTO> buscarContratoAprovacao(Integer codprop, Integer codcliente, Integer codcorretor, Integer acao){
        String query = "SELECT new com.cestec.cestec.model.contratoDTO( " +
                       "con.codcontrato, i.codimovel, p.codproprietario, c.codcliente, con.datinicio, con.datfinal, i.tipo, corr.codcorretor, i.preco, i.negociacao, p.nome, c.nome, func.nome, con.valor, i.quartos, i.vlrcondominio, i.area, c.documento, i.endereco, con.valorliberado, con.observacao) " +
                       "FROM pcp_contrato con "       +
                       "JOIN con.pcp_corretor corr "  +
                       "JOIN corr.funcionario func "  +
                       "JOIN con.pcp_proprietario p " +
                       "JOIN con.pcp_cliente c "      +
                       "JOIN con.pcp_imovel i ";

        boolean temand = false;

        if(codprop != null){
            query += (temand?" AND ":" WHERE ") + " p.codproprietario = :codprop";
            temand = true;
        }

        if(codcliente != null){
            query += (temand?" AND ":" WHERE ") + " c.codcliente = :codcliente";
            temand = true;
        }

        if(codcorretor != null){
            query += (temand?" AND ":" WHERE ") + " corr.codcorretor = :codcorretor";
            temand = true;
        }

        if(acao == 1){
            query += (temand?" AND ":" WHERE ") + " con.situacao = :acao";
            temand = true;
        }

        var q = em.createQuery(query, contratoDTO.class);

        if(codprop != null){
            q.setParameter("codprop", codprop);
        }
        if(codcliente != null){
            q.setParameter("codcliente", codcliente);
        }
        if(codcorretor != null){
            q.setParameter("codcorretor", codcorretor);
        }
        if(acao == 1){
            q.setParameter("acao", acao);
        }

        return q.getResultList();
    }

    public List<pcp_cliente> buscarClientes(Integer codcliente){
        String query = "SELECT c FROM pcp_cliente AS c ";

        if(codcliente != null && codcliente != 0){
            query += " WHERE c.codcliente = :codcliente";
        }

        var q = em.createQuery(query, pcp_cliente.class);

        if(codcliente != null && codcliente != 0){
            q.setParameter("codcliente", codcliente);
        }

        return q.getResultList();
    }

    public List<pcp_proprietario> buscarProprietario(Integer codprop){
        String query = "SELECT p FROM pcp_proprietario AS p ";

        if(codprop != null && codprop != 0){
            query += " WHERE p.codproprietario = :codprop";
        }

        var q = em.createQuery(query, pcp_proprietario.class);

        if(codprop != null && codprop != 0){
            q.setParameter("codprop", codprop);
        }

        return q.getResultList();
    }

    public List<contratoDTO> buscarContratoGrid(Integer codprop, Integer codcliente){
        String query = "SELECT new com.cestec.cestec.model.contratoDTO( " +
                       "con.codcontrato, c.codcliente, c.nome, p.codproprietario, p.nome, i.codimovel, i.tipo, i.negociacao, i.preco, con.datinicio, con.datfinal, con.valor, i.endereco, corr.codcorretor) " +
                       "FROM pcp_contrato con "       + 
                       "JOIN con.pcp_proprietario p " + 
                       "JOIN con.pcp_corretor corr "  +
                       "JOIN con.pcp_cliente c "      +
                       "JOIN con.pcp_imovel i";

        boolean temand = false;

        if(codprop != null && codprop != 0){
            query += (temand?" AND ":" WHERE ") + " p.codproprietario = :codprop";
            temand = true;
        }

        if(codcliente != null && codcliente != 0){
            query += (temand?" AND ":" WHERE ") + " c.codcliente = :codcliente";
            temand = true;
        }

        var q = em.createQuery(query, contratoDTO.class);

        if(codprop != null && codprop != 0){
            q.setParameter("codprop", codprop);
        }
        if(codcliente != null && codcliente != 0){
            q.setParameter("codcliente", codcliente);
        }

        return q.getResultList();
    }
}
