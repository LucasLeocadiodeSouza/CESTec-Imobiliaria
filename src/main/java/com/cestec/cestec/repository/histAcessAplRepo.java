package com.cestec.cestec.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.historicoAcessoAplDTO;
import com.cestec.cestec.model.sp_histacessapl;

@Repository
public interface histAcessAplRepo extends JpaRepository<sp_histacessapl, Integer> {
    @Query("SELECT histacess FROM sp_histacessapl histacess WHERE histacess.id = :cod")
    sp_histacessapl findHistAcessAplById(@Param("cod") Integer cod);

    @Query("SELECT new com.cestec.cestec.model.historicoAcessoAplDTO ( "
        + " histacess.id, histacess.idmodulos.id, histacess.idaplicacao.id, histacess.idfunc.codfuncionario, histacess.idaplicacao.descricao, histacess.idmodulos.descricao, histacess.numacess) "
        + " FROM sp_histacessapl histacess "
        + " JOIN histacess.idfunc.sp_user sp "
        + " WHERE sp.login = :ideusu")
    List<historicoAcessoAplDTO> findAllHistAcessAplByIdeusu(@Param("ideusu") String ideusu);

    @Query("SELECT h FROM sp_histacessapl h "
         + "WHERE h.ideusu = :ideusu AND "
         + " h.idmodulos.id = :codmod AND " 
         + " h.idaplicacao.id = :codapl")
    sp_histacessapl findByAplicacao(@Param("ideusu") String ideusu, @Param("codmod") Integer codmod, @Param("codapl") Integer codapl);
}
