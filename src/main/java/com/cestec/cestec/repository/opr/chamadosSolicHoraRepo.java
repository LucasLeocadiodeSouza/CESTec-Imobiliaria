package com.cestec.cestec.repository.opr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.opr_chamados_solic_horario;
import com.cestec.cestec.model.opr.opr_chamados_solic_horarioId;

@Repository
public interface chamadosSolicHoraRepo extends JpaRepository<opr_chamados_solic_horario, opr_chamados_solic_horarioId>{
    
}
