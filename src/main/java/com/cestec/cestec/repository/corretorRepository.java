package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.cri.corretorDTO;
import com.cestec.cestec.model.cri.pcp_corretor;

@Repository
public interface corretorRepository extends JpaRepository<pcp_corretor, Integer>{    
    @Query("SELECT new com.cestec.cestec.model.cri.corretorDTO ( "
        + " cor.codcorretor, cor.email, func.codfuncionario, func.nome, sp.login) "
        + " FROM pcp_corretor cor "
        + " JOIN cor.funcionario func "
        + " JOIN func.sp_user sp "
        + " WHERE sp.login = :login")
    corretorDTO findCorretorDTOByIdeusu(String login);

    @Query("SELECT new com.cestec.cestec.model.cri.pcp_corretor ( "
         + " cor.codcorretor, cor.funcionario, cor.email) "
         + " FROM pcp_corretor cor "
         + " JOIN cor.funcionario func "
         + " JOIN func.sp_user sp "
         + " WHERE sp.login = :login")
    pcp_corretor findCorretorByIdeusu(@Param("login") String login);

    @Query("SELECT new com.cestec.cestec.model.cri.pcp_corretor ( "
         + " cor.codcorretor, cor.funcionario, cor.email) "
         + " FROM pcp_corretor cor "
         + " WHERE cor.codcorretor = :idlogin")
    pcp_corretor findCorretorById(@Param("idlogin") Integer idlogin);
}
