package com.cestec.cestec.repository.spf;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.spf.sp_usu_aplfav;
import com.cestec.cestec.model.spf.sp_usu_aplfavId;

@Repository
public interface usuAplicacoesFavRepo extends JpaRepository<sp_usu_aplfav, sp_usu_aplfavId>{
    @Query("SELECT aplfav FROM sp_usu_aplfav aplfav WHERE aplfav.id.id_funcionario = :idfunc")
    List<sp_usu_aplfav> findAllAplFavsByUsu(@Param("idfunc") Integer idfunc);

    @Query("SELECT aplfav FROM sp_usu_aplfav aplfav WHERE aplfav.id.id_funcionario = :idfunc AND aplfav.id.id_aplicacao = :idapl")
    sp_usu_aplfav findAplFavById(@Param("idfunc") Integer idfunc, @Param("idapl") Integer idapl);
}
