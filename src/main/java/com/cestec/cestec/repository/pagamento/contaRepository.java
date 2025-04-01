package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cestec.cestec.model.contasAPagar.Conta;

public interface contaRepository extends JpaRepository<Conta, Long>{
    Conta findById(Integer id);
}
