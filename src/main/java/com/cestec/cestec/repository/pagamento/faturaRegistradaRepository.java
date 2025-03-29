package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cestec.cestec.model.contasAPagar.FaturaRegistrada;

public interface faturaRegistradaRepository extends JpaRepository<FaturaRegistrada, Long>{
    
}
