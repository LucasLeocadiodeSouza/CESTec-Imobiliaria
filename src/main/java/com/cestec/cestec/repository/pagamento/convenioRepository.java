package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cestec.cestec.model.contasAPagar.Convenio;

public interface convenioRepository extends JpaRepository<Convenio, Long>{
    Convenio findById(Integer id);
}
