package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cestec.cestec.model.contasAPagar.Conta;

public interface contaRepository extends JpaRepository<Conta, Long>{
    @Query("SELECT con FROM Conta con WHERE id = :id")
    Conta findContaById(@Param("id") Integer id);
}
