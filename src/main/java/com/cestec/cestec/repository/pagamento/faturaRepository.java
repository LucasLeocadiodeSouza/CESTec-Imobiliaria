package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.contasAPagar.Fatura;

@Repository
public interface faturaRepository extends JpaRepository<Fatura, Long>{
    Fatura findById(Integer id);
}
