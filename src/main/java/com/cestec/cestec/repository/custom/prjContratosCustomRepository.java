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

    public List<ImovelProprietarioDTO> buscarImoveisGrid(Integer codcontrato, Integer codprop, Integer tipimovel){
        String query = "SELECT new com.cestec.cestec.model.ImovelProprietarioDTO( " +
                       "i.codimovel," + 
                       "i.pcp_proprietario.codproprietario, " +
                       "i.pcp_proprietario.nome, " +
                       "i.tipo, " +
                       "i.status, " +
                       "i.preco, " +
                       "i.negociacao, " +
                       "i.endereco_bairro," +
                       "i.endereco_numero," +
                       "i.endereco_postal," +
                       "i.endereco_cidade," +
                       "i.endereco_estado," +
                       "i.endereco_rua," +
                       "i.area, " +
                       "i.quartos, " +
                       "i.vlrcondominio, " +
                       "i.datinicontrato, " +
                       "i.banheiros) " +
                       "FROM pcp_imovel i ";

        boolean temand = false;

        if(codcontrato != null && codcontrato != 0){
            pcp_contrato contratos = contratoRepo.findByCodContrato(codcontrato);
            query += " WHERE i.codimovel = " + contratos.getPcp_imovel().getCodimovel();
            temand = true;
        }

        if(codprop != null && codprop != 0){
            query += (temand?" AND ":" WHERE ") + " i.pcp_proprietario.codproprietario = :codpropri";
            temand = true;
        }

        if(tipimovel != null && tipimovel != 0){
            query += (temand?" AND ":" WHERE ") + " i.negociacao = :tipoimovel";
            temand = true;
        }

        var q = em.createQuery(query, ImovelProprietarioDTO.class);

        if(codprop != null && codprop != 0) q.setParameter("codpropri", codprop);

        if(tipimovel != null && tipimovel != 0) q.setParameter("tipoimovel", tipimovel);

        return q.getResultList();
    }

    public List<contratoDTO> buscarContratoAprovacaoGrid(Integer codprop, Integer codcliente, Integer codcorretor, Integer tipimovel, Integer acao){
        String query = "SELECT new com.cestec.cestec.model.cri.contratoDTO( " +
                       "con.id," + 
                       "con.pcp_imovel.codimovel, " + 
                       "con.pcp_proprietario.codproprietario, " + 
                       "con.pcp_cliente.codcliente, " + 
                       "con.datinicio, " + 
                       "con.datfinal, " + 
                       "con.pcp_imovel.tipo, " + 
                       "con.pcp_corretor.codcorretor, " + 
                       "con.pcp_imovel.preco, " + 
                       "con.pcp_imovel.negociacao," + 
                       "con.pcp_proprietario.nome, " + 
                       "con.pcp_cliente.nome, " + 
                       "con.pcp_corretor.funcionario.nome, " + 
                       "con.valor, " + 
                       "con.pcp_imovel.quartos, " + 
                       "con.pcp_imovel.banheiros, " + 
                       "con.pcp_imovel.vlrcondominio," + 
                       "con.pcp_imovel.area, " + 
                       "con.pcp_cliente.documento, " + 
                       "con.pcp_imovel.endereco_bairro," +
                       "con.pcp_imovel.endereco_numero," +
                       "con.pcp_imovel.endereco_postal," +
                       "con.pcp_imovel.endereco_cidade," +
                       "con.pcp_imovel.endereco_estado," +
                       "con.pcp_imovel.endereco_rua," +
                       "con.valorliberado, " + 
                       "con.observacao, " + 
                       "con.pcp_cliente.pessoa_fisica) " +
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

        if(codcorretor != null && codcorretor != 0){
            query += (temand?" AND ":" WHERE ") + " con.pcp_corretor.codcorretor = :codcorretor";
            temand = true;
        }

        if(tipimovel != null && tipimovel != 0){
            query += (temand?" AND ":" WHERE ") + " con.pcp_imovel.negociacao = :tipoimovel";
            temand = true;
        }

        if(acao == 1){
            query += (temand?" AND ":" WHERE ") + " con.situacao = 2";
            temand = true;
        }

        var q = em.createQuery(query, contratoDTO.class);

        if(codprop != null && codprop != 0) q.setParameter("codprop", codprop);

        if(codcliente != null && codcliente != 0)  q.setParameter("codcliente", codcliente);
        
        if(codcorretor != null && codcorretor != 0) q.setParameter("codcorretor", codcorretor);

        if(tipimovel != null && tipimovel != 0) q.setParameter("tipoimovel", tipimovel);

        return q.getResultList();
    }

    public List<pcp_cliente> buscarClientes(Integer codcliente){
        String query = "SELECT c FROM pcp_cliente AS c ";

        if(codcliente != null && codcliente != 0) query += " WHERE c.codcliente = :codcliente";

        var q = em.createQuery(query, pcp_cliente.class);

        if(codcliente != null && codcliente != 0) q.setParameter("codcliente", codcliente);

        return q.getResultList();
    }

    public List<pcp_proprietario> buscarProprietario(Integer codprop){
        String query = "SELECT p FROM pcp_proprietario AS p ";

        if(codprop != null && codprop != 0) query += " WHERE p.codproprietario = :codprop";

        var q = em.createQuery(query, pcp_proprietario.class);

        if(codprop != null && codprop != 0) q.setParameter("codprop", codprop);

        return q.getResultList();
    }

    public List<contratoDTO> buscarContratoGrid(Integer codprop, Integer codcliente, Integer tipimovel){
        String query = "SELECT new com.cestec.cestec.model.cri.contratoDTO( " +
                       "con.id, " + 
                       "con.pcp_cliente.codcliente, " + 
                       "con.pcp_cliente.nome, " + 
                       "con.pcp_proprietario.codproprietario, " + 
                       "con.pcp_proprietario.nome, " + 
                       "con.pcp_imovel.codimovel, " + 
                       "con.pcp_imovel.tipo, " + 
                       "con.pcp_imovel.negociacao, " + 
                       "con.pcp_imovel.preco, " + 
                       "con.datinicio, " +
                       "con.datfinal, " +
                       "con.valor, " +
                       "con.pcp_imovel.endereco_bairro," +
                       "con.pcp_imovel.endereco_numero," +
                       "con.pcp_imovel.endereco_postal," +
                       "con.pcp_imovel.endereco_cidade," +
                       "con.pcp_imovel.endereco_estado," +
                       "con.pcp_imovel.endereco_rua," +
                       "con.pcp_corretor.codcorretor," +
                       "con.situacao) " +
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
