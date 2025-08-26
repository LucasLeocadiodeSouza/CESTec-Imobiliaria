package com.cestec.cestec.repository.spf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.spf.sp_bloqueia_acess;

@Repository
public interface bloqueioAcessRepo extends JpaRepository<sp_bloqueia_acess, Integer> {
    
}
