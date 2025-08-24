package com.cestec.cestec.repository.opr;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.opr.opr_versionamento;
import com.cestec.cestec.model.opr.opr_versionamentoId;

@Repository
public interface versionamentoRepo extends JpaRepository<opr_versionamento, opr_versionamentoId>{
    List<opr_versionamento> findByChamadoSolicId(Integer idChamadosSolic);
}
