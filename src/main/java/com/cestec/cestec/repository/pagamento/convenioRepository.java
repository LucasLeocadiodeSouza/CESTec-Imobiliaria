package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cestec.cestec.model.contasAPagar.Convenio;

public interface convenioRepository extends JpaRepository<Convenio, Long>{
    @Query("SELECT con FROM Convenio con WHERE id = :id")
    Convenio findConvenioById(@Param("id") Integer id);
}
