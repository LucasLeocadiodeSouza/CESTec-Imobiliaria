package com.cestec.cestec.repository.opr;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.opr_agendamentos_setor;

@Repository
public interface agendamentosSetorRepo extends JpaRepository <opr_agendamentos_setor, Integer>{
    @Query("SELECT agendset FROM opr_agendamentos_setor agendset WHERE agendset.codagenda.id = :codagend")
    List<opr_agendamentos_setor> findAllByCodAgend(@Param("codagend") Integer codagend);

    @Query("SELECT agendset FROM opr_agendamentos_setor agendset WHERE agendset.codagenda.id = :codagend AND agendset.codsetor.id = :setor")
    opr_agendamentos_setor findSetorInAgend(@Param("codagend") Integer codagend, @Param("setor") Integer setor);

    @Query("SELECT agendset FROM opr_agendamentos_setor agendset WHERE " 
         + " agendset.codsetor.id = :setor AND"
         + " agendset.codagenda.datagen >= CURRENT_DATE")
    List<opr_agendamentos_setor> findAllByCodSetor(@Param("setor") Integer setor);
}
