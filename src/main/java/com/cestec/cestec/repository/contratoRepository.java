package com.cestec.cestec.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.contratoDTO;
import com.cestec.cestec.model.pcp_contrato;

@Repository
public interface contratoRepository extends JpaRepository<pcp_contrato, Integer> {
    
    @Query("SELECT new com.cestec.cestec.model.contratoDTO( " +
            "con.codcontrato, c.codcliente, c.nome, p.codproprietario, p.nome, i.codimovel, i.tipo, i.negociacao, i.preco, con.datinicio, con.datfinal, con.valor, i.endereco, corr.codcorretor) " +
            "FROM pcp_contrato con " + 
            "JOIN con.pcp_proprietario p " + 
            "JOIN con.pcp_corretor corr "  +
            "JOIN con.pcp_cliente c " +
            "JOIN con.pcp_imovel i")
    List<contratoDTO> buscarContratoGrid();
    
    @Query("SELECT new com.cestec.cestec.model.contratoDTO( " +
            "con.codcontrato, i.codimovel, p.codproprietario, c.codcliente, con.datinicio, con.datfinal, i.tipo, corr.codcorretor, i.preco, i.negociacao, p.nome, c.nome, func.nome, con.valor, i.quartos, i.vlrcondominio, i.area, c.documento, i.endereco, con.valorliberado, con.observacao) " +
            "FROM pcp_contrato con "       +
            "JOIN con.pcp_corretor corr "  +
            "JOIN corr.funcionario func "  +
            "JOIN con.pcp_proprietario p " +
            "JOIN con.pcp_cliente c "      +
            "JOIN con.pcp_imovel i  ")
    List<contratoDTO> buscarContratoAprovacao(@Param("acao") Integer acao);

    @Query("SELECT con.valor "          +
          " FROM pcp_contrato con "     +
          " JOIN con.pcp_corretor cor " +
          " JOIN pcp_meta meta ON meta.pcp_corretor = cor "    +
          " JOIN cor.funcionario func " +
          " JOIN func.sp_user sp "      +
          " WHERE sp.login = :ideusu "  +
          " AND con.situacao = 2 " +
          " AND con.datiregistro BETWEEN meta.datinicio AND meta.datfinal")          
    List<Double> buscarValoresContratoByIdeusu(@Param("ideusu") String ideusu);
}
