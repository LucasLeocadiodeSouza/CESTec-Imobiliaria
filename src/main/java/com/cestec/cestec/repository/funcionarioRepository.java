package com.cestec.cestec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.funcionario;

@Repository
public interface funcionarioRepository extends JpaRepository<funcionario, Integer>{
    @Query("SELECT i FROM funcionario i WHERE i.sp_user.login = :ideusu")
    funcionario findByUser(@Param("ideusu") String ideusu);
}
