package com.cestec.cestec.repository.cri;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.cri.contratoDTO;
import com.cestec.cestec.model.cri.pcp_contrato;

@Repository
public interface contratoRepository extends JpaRepository<pcp_contrato, Integer> {
    pcp_contrato findTopByOrderByIdDesc();

    @Query("SELECT contr FROM pcp_contrato contr WHERE contr.id = :codcontr")
    pcp_contrato findByCodContrato(@Param("codcontr") Integer codcontr);

    @Query("SELECT new com.cestec.cestec.model.cri.contratoDTO( " +
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
            "con.pcp_corretor.codcorretor, " + 
            "con.situacao) " +
            "FROM pcp_contrato con ")
    List<contratoDTO> buscarContratoGrid();
    
    @Query("SELECT new com.cestec.cestec.model.cri.contratoDTO( " +
            "con.id, " +
            "con.pcp_imovel.codimovel, " +
            "con.pcp_proprietario.codproprietario, " +
            "con.pcp_cliente.codcliente, "+
            "con.datinicio, " + 
            "con.datfinal, " +
            "con.pcp_imovel.tipo, " + 
            "con.pcp_corretor.codcorretor, "+
            "con.pcp_imovel.preco, " + 
            "con.pcp_imovel.negociacao, " + 
            "con.pcp_proprietario.nome, " +
            "con.pcp_cliente.nome, " +
            "con.pcp_corretor.funcionario.nome, " + 
            "con.valor, " + 
            "con.pcp_imovel.quartos, " + 
            "con.pcp_imovel.banheiros, " +
            "con.pcp_imovel.vlrcondominio, " + 
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
            "con.pcp_cliente.pessoa_fisica, " +
            "con.situacao) " +
            "FROM pcp_contrato con ")
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
    List<BigDecimal> buscarValoresContratoByIdeusu(@Param("ideusu") String ideusu);
}
