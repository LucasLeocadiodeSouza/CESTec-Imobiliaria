package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.cargo;

@Repository
public interface cargoRepository extends JpaRepository<cargo, Integer>{
    @Query("SELECT car FROM cargo car WHERE car.id = :codcargo")
    cargo findCargoByCodCargo(@Param("codcargo") Integer codcargo);
}
