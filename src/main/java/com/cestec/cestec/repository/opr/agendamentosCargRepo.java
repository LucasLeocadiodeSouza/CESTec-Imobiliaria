package com.cestec.cestec.repository.opr;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.opr_agendamentos_cargo;

@Repository
public interface agendamentosCargRepo extends JpaRepository <opr_agendamentos_cargo, Integer>{
    @Query("SELECT agendcarg FROM opr_agendamentos_cargo agendcarg WHERE agendcarg.codagenda.id = :codagend")
    List<opr_agendamentos_cargo> findAllByCodAgend(@Param("codagend") Integer codagend);

    @Query("SELECT agendcarg FROM opr_agendamentos_cargo agendcarg WHERE agendcarg.codagenda.id = :codagend AND agendcarg.codcargo.id = :cargo")
    opr_agendamentos_cargo findCargoInAgend(@Param("codagend") Integer codagend, @Param("cargo") Integer cargo);

    @Query("SELECT agendcarg FROM opr_agendamentos_cargo agendcarg WHERE " 
         + " agendcarg.codcargo.id = :cargo AND"
         + " agendcarg.codagenda.datagen >= CURRENT_DATE")
    List<opr_agendamentos_cargo> findAllByCodCargo(@Param("cargo") Integer cargo);
}
