package com.cestec.cestec.repository.opr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.opr_chamados_solic;

@Repository
public interface chamadosSolicRepo extends  JpaRepository<opr_chamados_solic, Integer>{
    @Query("SELECT solic FROM opr_chamados_solic solic WHERE solic.id = :idsolic")
    opr_chamados_solic findByIdSolic(@Param("idsolic") Integer idsolic);
}
