package com.cestec.cestec.repository.cri;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cestec.cestec.model.cri.pcp_imovel_img;
import com.cestec.cestec.model.cri.pcp_imovel_imgId;

public interface imovelImgRepo extends JpaRepository<pcp_imovel_img, pcp_imovel_imgId>{
    @Query("SELECT imagens FROM pcp_imovel_img imagens WHERE imagens.id.codimovel = :codimovel")
    List<pcp_imovel_img> findAllImgByCodimovel(@Param("codimovel") Integer codimovel);
}
