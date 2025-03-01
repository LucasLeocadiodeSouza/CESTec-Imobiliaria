package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.pcp_cliente;

@Repository
public interface clienteRepository extends JpaRepository<pcp_cliente, Integer> {
    pcp_cliente findByCodcliente(Integer codcliente);
}
