package com.cestec.cestec.repository.spf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_resp;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_respId;

@Repository
public interface bloqueioAcessRespRepo extends JpaRepository<sp_bloqueia_acess_resp, sp_bloqueia_acess_respId> {
    @Query("SELECT bloqresp FROM sp_bloqueia_acess_resp bloqresp WHERE bloqresp.seq.id_bloqueia_acess = :idbloqacess")
    List<sp_bloqueia_acess_resp> findAllBloqueioResp(@Param("idbloqacess") Integer idbloqacess);
}
