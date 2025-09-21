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
                     + "fat.pcp_cliente.codcliente,fat.pcp_cliente.nome,fat.pcp_cliente.documento,fat.pcp_contrato.id,fat.pcp_contrato.pcp_imovel.codimovel,fat.id,fat.valor,fat.data_vencimento,fat.tipoPagamento,fat.situacao,fat.numeroDocumento,fat.conta.id)"
                     + "FROM Fatura fat ";
        
        var q = em.createQuery(query, faturamentoDTO.class);
        
        return q.getResultList();
    }
}
