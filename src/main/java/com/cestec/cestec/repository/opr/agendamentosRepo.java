package com.cestec.cestec.repository.opr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.opr_agendamentos;

@Repository
public interface agendamentosRepo extends JpaRepository<opr_agendamentos, Integer>{
    @Query("SELECT agendfunc FROM opr_agendamentos agendfunc WHERE agendfunc.id = :codagend")
    opr_agendamentos findAgenByCodAgend(@Param("codagend") Integer codagend);
}
