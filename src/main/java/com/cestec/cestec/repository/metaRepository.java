package com.cestec.cestec.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.pcp_meta;
import com.cestec.cestec.model.cri.corretorDTO;

@Repository
public interface metaRepository extends JpaRepository<pcp_meta, Integer>{
    @Query("SELECT new com.cestec.cestec.model.cri.corretorDTO( " 
         + "meta.codmeta,cor.codcorretor, func.nome, meta.valor_meta, meta.datinicio, meta.datfinal, meta.situacao) " 
         + " FROM pcp_meta meta"
         + " JOIN meta.pcp_corretor cor "
         + " JOIN cor.funcionario func")
    List<corretorDTO> findAllMetasGrid();

    @Query("SELECT new com.cestec.cestec.model.cri.corretorDTO( " 
         + "meta.codmeta,cor.codcorretor, func.nome, meta.valor_meta, meta.datinicio, meta.datfinal, meta.situacao) " 
         + " FROM pcp_meta meta"
         + " JOIN meta.pcp_corretor cor "
         + " JOIN cor.funcionario func"
         + " JOIN func.sp_user sp "
         + " WHERE sp.login = :ideusu")
    List<corretorDTO> findMetaMensalByIdeusu(@Param("ideusu") String ideusu);

    @Query("SELECT meta " 
         + " FROM pcp_meta meta"
         + " JOIN meta.pcp_corretor cor "
         + " JOIN cor.funcionario func"
         + " JOIN func.sp_user sp "
         + " WHERE sp.login = :ideusu "
         + " AND CURRENT_DATE BETWEEN meta.datinicio AND meta.datfinal ")
    pcp_meta findMesMetaByIdeusu(@Param("ideusu") String ideusu);
}
