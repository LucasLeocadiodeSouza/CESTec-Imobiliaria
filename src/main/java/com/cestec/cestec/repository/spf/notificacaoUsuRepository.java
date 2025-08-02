package com.cestec.cestec.repository.spf;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cestec.cestec.model.spf.sp_notificacao_usu;

public interface notificacaoUsuRepository extends JpaRepository<sp_notificacao_usu, Integer> {
    @Query("SELECT noti FROM sp_notificacao_usu noti WHERE " 
         + " noti.func.sp_user.login = :ideusu"
         + " ORDER BY noti.datregistro DESC, noti.id DESC")
    List<sp_notificacao_usu> findAllByIdeusu(@Param("ideusu") String ideusu);
}
