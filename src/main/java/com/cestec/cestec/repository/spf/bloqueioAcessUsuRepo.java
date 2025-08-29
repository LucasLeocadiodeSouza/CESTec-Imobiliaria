package com.cestec.cestec.repository.spf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.spf.sp_bloqueia_acess_usu;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_usuId;

@Repository
public interface bloqueioAcessUsuRepo extends JpaRepository<sp_bloqueia_acess_usu, sp_bloqueia_acess_usuId>{
    @Query("SELECT bloqusu FROM sp_bloqueia_acess_usu bloqusu WHERE bloqusu.seq.id_bloqueia_acess = :idbloqacess AND bloqusu.ativo = true")
    List<sp_bloqueia_acess_usu> findAllBloqueioUsuAtivos(@Param("idbloqacess") Integer idbloqacess);

    @Query("SELECT bloqusu FROM sp_bloqueia_acess_usu bloqusu WHERE bloqusu.seq.id_bloqueia_acess = :idbloqacess")
    List<sp_bloqueia_acess_usu> findAllBloqueioUsu(@Param("idbloqacess") Integer idbloqacess);
}
