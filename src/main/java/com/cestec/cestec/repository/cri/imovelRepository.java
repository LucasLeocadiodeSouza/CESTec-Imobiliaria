package com.cestec.cestec.repository.cri;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.cri.pcp_imovel;

@Repository
public interface imovelRepository extends JpaRepository<pcp_imovel, Integer> {
    @Query("SELECT i FROM pcp_imovel i WHERE i.codimovel = :codimovel")
    pcp_imovel findByCodimovel(@Param("codimovel") Integer codimovel);

    @Query("SELECT i FROM pcp_imovel i WHERE i.codimovel = :codimovel AND i.pcp_proprietario.codproprietario = :codprop")
    pcp_imovel findByCodimovelAndCodprop(@Param("codimovel") Integer codimovel, @Param("codprop") Integer codprop);

    @Query("SELECT i FROM pcp_imovel i WHERE i.pcp_proprietario.codproprietario = :codproprietario")
    List<pcp_imovel> findByProprietario(@Param("codproprietario") Integer codproprietario);

    @Query("SELECT i FROM pcp_imovel i WHERE i.pcp_proprietario.codproprietario = :codproprietario AND i.status = 1")
    List<pcp_imovel> findAtivosByProprietario(@Param("codproprietario") Integer codproprietario);

    @Query("SELECT i FROM pcp_imovel i WHERE i.status = 1")
    List<pcp_imovel> findAtivos();

    @Query("SELECT i FROM pcp_imovel i WHERE i.pcp_proprietario.codproprietario = :codproprietario AND i.codimovel = :codimovel")
    pcp_imovel existeimovel(@Param("codimovel") Integer codimovel, @Param("codproprietario") Integer codproprietario);

    @Query("SELECT new com.cestec.cestec.model.ImovelProprietarioDTO(" +
           "i.codimovel," + 
           "p.codproprietario," + 
           "p.nome," + 
           "i.tipo, " + 
           "i.status," + 
           "i.preco, " + 
           "i.negociacao, " +
           "i.endereco_bairro," +
           "i.endereco_numero," +
           "i.endereco_postal," +
           "i.endereco_cidade," +
           "i.endereco_estado," +
           "i.endereco_rua," +
           "i.area, " +
           "i.quartos, " +
           "i.vlrcondominio, " +
           "i.datinicontrato, " +
           "i.banheiros) " +
           "FROM pcp_imovel i JOIN i.pcp_proprietario p")
    List<ImovelProprietarioDTO> buscarimoveis();    

    @Query("SELECT new com.cestec.cestec.model.ImovelProprietarioDTO( " + 
           "i.codimovel," + 
           "p.codproprietario," + 
           "p.nome," + 
           "i.tipo, " + 
           "i.status," + 
           "i.preco, " + 
           "i.negociacao, " +
           "i.endereco_bairro," +
           "i.endereco_numero," +
           "i.endereco_postal," +
           "i.endereco_cidade," +
           "i.endereco_estado," +
           "i.endereco_rua," +
           "i.area, " +
           "i.quartos, " +
           "i.vlrcondominio, " +
           "i.datinicontrato, " +
           "i.banheiros) " +
           "FROM pcp_imovel i JOIN i.pcp_proprietario p " +
           "WHERE i.codimovel = :codimovel AND "+
           "p.codproprietario = :codproprietario")
    ImovelProprietarioDTO buscarImovelGrid(@Param("codimovel") Integer codimovel,@Param("codproprietario") Integer codproprietario);
}
