package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cestec.cestec.model.pcp_proprietario;

public interface proprietarioRepository extends JpaRepository<pcp_proprietario, Integer> {
    pcp_proprietario findByCodproprietario(Integer codproprietario);
}
