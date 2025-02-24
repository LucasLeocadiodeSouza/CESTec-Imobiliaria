package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cestec.cestec.model.pcp_imovel_tipo;
import com.cestec.cestec.model.pcp_imovel_tipoID;

public interface imovelTipoRepository extends JpaRepository<pcp_imovel_tipo, pcp_imovel_tipoID> {
}
