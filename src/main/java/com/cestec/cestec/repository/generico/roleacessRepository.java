package com.cestec.cestec.repository.generico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.aplicacaoDTO;
import com.cestec.cestec.model.sp_roleacess;

@Repository
public interface roleacessRepository extends JpaRepository<sp_roleacess, Integer> {
    @Query("SELECT new com.cestec.cestec.model.aplicacaoDTO( " + 
           "role.id, role.descricao) " + 
           "FROM sp_roleacess role")
    List<aplicacaoDTO> findAllRole();

    @Query("SELECT role "            +
           "FROM sp_roleacess role " +
           "WHERE role.id = :codrole")
    sp_roleacess findByCodRole(@Param("codrole") Integer codrole);
}
