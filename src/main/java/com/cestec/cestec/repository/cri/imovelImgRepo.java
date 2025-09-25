package com.cestec.cestec.repository.cri;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cestec.cestec.model.cri.pcp_imovel_img;
import com.cestec.cestec.model.cri.pcp_imovel_imgId;

public interface imovelImgRepo extends JpaRepository<pcp_imovel_img, pcp_imovel_imgId>{
    @Query("SELECT imagens FROM pcp_imovel_img imagens WHERE imagens.id.seq = :seq AND imagens.id.codimovel = :codimovel")
    pcp_imovel_img findImgById(@Param("seq") Integer seq, @Param("codimovel") Integer codimovel);

    @Query("SELECT imagens FROM pcp_imovel_img imagens WHERE imagens.id.codimovel = :codimovel")
    List<pcp_imovel_img> findAllImgByCodimovel(@Param("codimovel") Integer codimovel);

    @Query("SELECT MAX(i.id.seq) FROM pcp_imovel_img i WHERE i.id.codimovel = :codimovel")
    Integer findMaxSeqByCodimovel(@Param("codimovel") Integer codimovel);
}
