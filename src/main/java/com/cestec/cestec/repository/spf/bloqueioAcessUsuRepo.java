package com.cestec.cestec.repository.spf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.spf.sp_bloqueia_acess_usu;
import com.cestec.cestec.model.spf.sp_bloqueia_acess_usuId;

@Repository
public interface bloqueioAcessUsuRepo extends JpaRepository<sp_bloqueia_acess_usu, sp_bloqueia_acess_usuId>{
    
}
