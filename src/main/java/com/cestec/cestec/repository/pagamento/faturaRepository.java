package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.contasAPagar.Fatura;

@Repository
public interface faturaRepository extends JpaRepository<Fatura, Long>{
    @Query("SELECT fat FROM Fatura fat WHERE id = :id")
    Fatura findFaturaById(@Param("id") Long id);
}
