package com.cestec.cestec.repository.opr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.opr_agendamentos_func;

@Repository
public interface agendamentosFuncRepo extends JpaRepository <opr_agendamentos_func, Integer>{
    @Query("SELECT agendfunc FROM opr_agendamentos_func agendfunc WHERE agendfunc.id = :codagendfunc")
    opr_agendamentos_func findByCodAgendFunc(@Param("codagendfunc") Integer codagendfunc);

    @Query("SELECT agendfunc FROM opr_agendamentos_func agendfunc WHERE agendfunc.codagenda.id = :codagend AND agendfunc.codfunc.codfuncionario = :codfunc")
    opr_agendamentos_func findFuncInAgend(@Param("codagend") Integer codagend, @Param("codfunc") Integer codfunc);

    @Query("SELECT agendfunc FROM opr_agendamentos_func agendfunc WHERE agendfunc.codagenda.id = :codagend")
    List<opr_agendamentos_func> findAllByCodAgend(@Param("codagend") Integer codagend);

    @Query("SELECT agendfunc FROM opr_agendamentos_func agendfunc WHERE " 
         + " agendfunc.codfunc.sp_user.login = :ideusu AND"
         + " agendfunc.codagenda.datagen >= CURRENT_DATE")
    List<opr_agendamentos_func> findAllByCodFunc(@Param("ideusu") String ideusu);
}
