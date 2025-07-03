package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.pcp_setor;

@Repository
public interface setorRepository extends JpaRepository<pcp_setor, Integer>{

    @Query("SELECT set FROM pcp_setor set WHERE set.codsetor = :cod")
    pcp_setor findSetorByCodSetor(@Param("cod") Integer cod);
}
