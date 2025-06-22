package com.cestec.cestec.repository.pagamento;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.contasAPagar.faturamentoDTO;
import jakarta.persistence.EntityManager;

@Repository
public class pagamentoCustomRepo {
    private final EntityManager em;

    public pagamentoCustomRepo(EntityManager em){
        this.em = em;
    }
    
    public List<faturamentoDTO> buscarFaturaCliente(){
        String query = "SELECT new com.cestec.cestec.model.contasAPagar.faturamentoDTO( "
                     + "cliente.codcliente,cliente.nome,cliente.documento,contrato.codcontrato,fat.id,fat.valor,fat.data_vencimento,fat.tipoPagamento,fat.situacao,fat.numeroDocumento,conta.id)"
                     + "FROM Fatura fat "
                     + "JOIN fat.pcp_cliente cliente "
                     + "JOIN cliente.pcp_contrato contrato "
                     + "JOIN fat.conta conta ";
        
        var q = em.createQuery(query, faturamentoDTO.class);
        
        return q.getResultList();
    }
}
