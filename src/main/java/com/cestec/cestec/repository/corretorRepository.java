package com.cestec.cestec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cestec.cestec.model.corretorDTO;
import com.cestec.cestec.model.pcp_corretor;

@Repository
public interface corretorRepository extends JpaRepository<pcp_corretor, Integer>{    
    @Query("SELECT new com.cestec.cestec.model.corretorDTO ( "
        + " cor.codcorretor, cor.email, func.codfuncionario, func.nome, sp.login) "
        + " FROM pcp_corretor cor "
        + " JOIN cor.funcionario func "
        + " JOIN func.sp_user sp "
        + " WHERE sp.login = :login")
    corretorDTO findCorretorDTOByIdeusu(String login);

    @Query("SELECT new com.cestec.cestec.model.pcp_corretor ( "
         + " cor.codcorretor, cor.funcionario, cor.email) "
         + " FROM pcp_corretor cor "
         + " JOIN cor.funcionario func "
         + " JOIN func.sp_user sp "
         + " WHERE sp.login = :login")
    pcp_corretor findCorretorByIdeusu(@Param("login") String login);

    @Query("SELECT new com.cestec.cestec.model.pcp_corretor ( "
         + " cor.codcorretor, cor.funcionario, cor.email) "
         + " FROM pcp_corretor cor "
         + " JOIN cor.funcionario func "
         + " JOIN func.sp_user sp "
         + " WHERE sp.id = :idlogin")
    pcp_corretor findCorretorById(@Param("idlogin") Integer idlogin);
}
