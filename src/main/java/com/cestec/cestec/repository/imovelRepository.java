package com.cestec.cestec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cestec.cestec.model.pcp_imovel;

public interface imovelRepository extends JpaRepository<pcp_imovel, Integer> {
    @Query("SELECT i FROM pcp_imovel i WHERE i.pcp_proprietario.codproprietario = :codproprietario")
    List<pcp_imovel> findByProprietario(@Param("codproprietario") Integer codproprietario);
}
