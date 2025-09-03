package com.cestec.cestec.repository.cri;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cestec.cestec.model.cri.pcp_proprietario;

public interface proprietarioRepository extends JpaRepository<pcp_proprietario, Integer> {
    @Query("SELECT propri FROM pcp_proprietario propri WHERE propri.codproprietario = :codproprietario")
    pcp_proprietario findByCodproprietario(@Param("codproprietario") Integer codproprietario);
}
