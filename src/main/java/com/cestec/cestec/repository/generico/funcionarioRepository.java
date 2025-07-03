package com.cestec.cestec.repository.generico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.funcionario;

@Repository
public interface funcionarioRepository extends JpaRepository<funcionario, Integer>{
    @Query("SELECT i.nome FROM funcionario i WHERE i.sp_user.id = :id")
    String findNameByUser(@Param("id") Integer id);

    @Query("SELECT f FROM funcionario f WHERE f.codfuncionario = :codfunc")
    funcionario findFuncBycodfunc(@Param("codfunc") Integer codfunc);
    
    @Query("SELECT i.codfuncionario FROM funcionario i WHERE i.sp_user.login = :ideusu")
    Integer findCodFuncByIdeusu(@Param("ideusu") String ideusu);

    @Query("SELECT f.cargo.nome FROM funcionario f WHERE f.sp_user.id = :id")
    String findCargoIdByNome(@Param("id") Integer id);
}
