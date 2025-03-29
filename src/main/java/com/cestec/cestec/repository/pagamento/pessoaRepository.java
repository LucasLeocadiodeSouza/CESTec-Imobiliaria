package com.cestec.cestec.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.contasAPagar.Pessoa;

@Repository
public interface pessoaRepository extends JpaRepository<Pessoa, Long>{

}
