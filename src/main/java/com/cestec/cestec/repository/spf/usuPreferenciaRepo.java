package com.cestec.cestec.repository.spf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cestec.cestec.model.spf.sp_usu_preferencia;

public interface usuPreferenciaRepo extends JpaRepository<sp_usu_preferencia, Integer>{
    @Query("SELECT usuprefere FROM sp_usu_preferencia usuprefere WHERE usuprefere.func.sp_user.login = :ideusu")
    sp_usu_preferencia findByPreferencia(@Param("ideusu") String ideusu);
}