package com.cestec.cestec.repository.spf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.spf.sp_bloqueia_acess;

@Repository
public interface bloqueioAcessRepo extends JpaRepository<sp_bloqueia_acess, Integer> {
    @Query("SELECT bloq FROM sp_bloqueia_acess bloq WHERE bloq.id = :idbloq")
    sp_bloqueia_acess findByIdBloq(@Param("idbloq") Integer idbloq);
}
