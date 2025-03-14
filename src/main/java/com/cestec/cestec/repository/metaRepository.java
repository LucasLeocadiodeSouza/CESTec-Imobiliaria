package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.pcp_meta;

@Repository
public interface metaRepository extends JpaRepository<pcp_meta, Integer>{

}
