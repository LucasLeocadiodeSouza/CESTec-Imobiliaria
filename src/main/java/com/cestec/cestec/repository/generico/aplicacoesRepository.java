package com.cestec.cestec.repository.generico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.aplicacaoDTO;
import com.cestec.cestec.model.sp_aplicacoes;

@Repository
public interface aplicacoesRepository extends JpaRepository<sp_aplicacoes, Integer> {
    @Query("SELECT apl FROM sp_aplicacoes apl " +
           "WHERE apl.arquivo_inic = :arquivo_inic")
    sp_aplicacoes findByArquivo_inic(@Param("arquivo_inic") String arquivo_inic);

     @Query("SELECT apl FROM sp_aplicacoes apl " +
            "WHERE apl.id = :codapl")
    sp_aplicacoes findByCodApl(@Param("codapl") Integer codapl);

    @Query("SELECT new com.cestec.cestec.model.aplicacaoDTO( " +
           "sp_apl.id, sp_apl.descricao, sp_apl.arquivo_inic) " +
           "FROM sp_aplicacoes sp_apl " +
           "JOIN sp_apl.modulo sp_mod " +  
           "WHERE sp_mod.id = :modulo")
    List<aplicacaoDTO> findAplicacoesByModulo(@Param("modulo") Integer modulo);

    @Query("SELECT a FROM sp_aplicacoes a ORDER BY a.modulo.id")
    List<sp_aplicacoes> findAllOrderedByModulo();
}
