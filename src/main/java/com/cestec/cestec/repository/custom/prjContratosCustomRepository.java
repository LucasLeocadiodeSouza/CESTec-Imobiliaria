package com.cestec.cestec.repository.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.aplicacaoDTO;
import com.cestec.cestec.model.cri.contratoDTO;
import com.cestec.cestec.model.cri.pcp_cliente;
import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.model.cri.pcp_proprietario;
import com.cestec.cestec.repository.cri.contratoRepository;

import jakarta.persistence.EntityManager;

@Repository
public class prjContratosCustomRepository {
    
    private final EntityManager em;

    public prjContratosCustomRepository(EntityManager em){
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

    public List<contratoDTO> buscarContratoAprovacao(Integer codprop, Integer codcliente, Integer codcorretor, Integer acao){
        String query = "SELECT new com.cestec.cestec.model.cri.contratoDTO( " +
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

    public List<contratoDTO> buscarContratoGrid(Integer codprop, Integer codcliente, Integer tipimovel){
        String query = "SELECT new com.cestec.cestec.model.cri.contratoDTO( " +
                       "con.id.codcontrato, con.pcp_cliente.codcliente, con.pcp_cliente.nome, con.pcp_proprietario.codproprietario, con.pcp_proprietario.nome, con.pcp_imovel.codimovel, con.pcp_imovel.tipo, con.pcp_imovel.negociacao, con.pcp_imovel.preco, con.datinicio, con.datfinal, con.valor, con.pcp_imovel.endereco, con.pcp_corretor.codcorretor, con.situacao) " +
                       "FROM pcp_contrato con ";

        boolean temand = false;

        if(codprop != null && codprop != 0){
            query += (temand?" AND ":" WHERE ") + " con.pcp_proprietario.codproprietario = :codprop";
            temand = true;
        }

        if(codcliente != null && codcliente != 0){
            query += (temand?" AND ":" WHERE ") + " con.pcp_cliente.codcliente = :codcliente";
            temand = true;
        }

        if(tipimovel != null && tipimovel != 0){
            query += (temand?" AND ":" WHERE ") + " con.pcp_imovel.negociacao = :tipoimovel";
            temand = true;
        }

        var q = em.createQuery(query, contratoDTO.class);

        if(codprop != null && codprop != 0) q.setParameter("codprop", codprop);
        if(codcliente != null && codcliente != 0) q.setParameter("codcliente", codcliente);
        if(tipimovel != null && tipimovel != 0) q.setParameter("tipoimovel", tipimovel);

        return q.getResultList();
    }

    public List<aplicacaoDTO> buscarModulosApl(){
        String query = "SELECT new com.cestec.cestec.model.aplicacaoDTO( " +
                       "sp_apl.id, sp_apl.descricao, sp_mod.id, sp_mod.descricao, sp_apl.arquivo_inic) " +
                       "FROM sp_aplicacoes sp_apl " + 
                       "JOIN sp_apl.modulo sp_mod ";

        var q = em.createQuery(query, aplicacaoDTO.class);

        return q.getResultList();
    }
}
