package com.cestec.cestec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.model.pcp_contrato;

@Repository
public interface contratoRepository extends JpaRepository<pcp_contrato, Integer> {
    
    @Query("SELECT new com.cestec.cestec.model.contratoDTO( " +
            "con.codcontrato, c.codcliente, c.nome, p.codproprietario, p.nome, i.codimovel, i.tipo, i.negociacao, i.preco) " +
            "FROM pcp_contrato con " + 
            "JOIN con.pcp_proprietario p " + 
            "JOIN con.pcp_cliente c " +
            "JOIN con.pcp_imovel i")
    List<contratoDTO> buscarContratoGrid();
    

}
