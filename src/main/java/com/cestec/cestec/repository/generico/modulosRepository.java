package com.cestec.cestec.repository.generico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cestec.cestec.model.sp_modulos;

public interface modulosRepository extends JpaRepository<sp_modulos, Integer> {
    @Query("SELECT mod FROM sp_modulos mod WHERE mod.id = :codmod")
    sp_modulos findByIdModulos(@Param("codmod") Integer codmod);
}
