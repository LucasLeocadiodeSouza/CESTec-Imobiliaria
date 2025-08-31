package com.cestec.cestec.repository.spf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.spf.sp_bloqueia_acess;

@Repository
public interface bloqueioAcessRepo extends JpaRepository<sp_bloqueia_acess, Integer> {
    @Query("SELECT bloq FROM sp_bloqueia_acess bloq WHERE bloq.id = :idbloq")
    sp_bloqueia_acess findByIdBloq(@Param("idbloq") Integer idbloq);

    @Query("SELECT bloq FROM sp_bloqueia_acess bloq WHERE bloq.modulo.id = :idmod AND bloq.aplicacao.id = :idapl")
    sp_bloqueia_acess findByModuloIhAplicacao(@Param("idmod") Integer idmod, @Param("idapl") Integer idapl);

    @Query("SELECT bloq FROM sp_bloqueia_acess bloq WHERE bloq.modulo.id = :idmod AND bloq.aplicacao.id = :idapl")
    List<sp_bloqueia_acess> findAllByModuloIhAplicacao(@Param("idmod") Integer idmod, @Param("idapl") Integer idapl);
    
    @Query("SELECT bloq FROM sp_bloqueia_acess bloq WHERE bloq.id = :idbloq AND bloq.ideusu = :ideusu")
    sp_bloqueia_acess usuarioEhQuemCadastrou(@Param("idbloq") Integer idbloq, @Param("ideusu") String ideusu);
}
