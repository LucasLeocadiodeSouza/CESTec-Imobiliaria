package com.cestec.cestec.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class aplicacaoDTO {
    
    private Integer id;
    private String  descricao_apl;
    private String  descricao_mod;
    private Integer idmodulo;
    private Integer role;
    private String  arquivo_inic;
    private Date    datregistro;

    public aplicacaoDTO(Integer id, String descricao_apl, String arquivo_inic) {
        this.id            = id;
        this.descricao_apl = descricao_apl;
        this.arquivo_inic  = arquivo_inic;
    }

    public aplicacaoDTO(Integer idmodulo, String descricao_mod) {
        this.idmodulo      = idmodulo;
        this.descricao_mod = descricao_mod;
    }

    public aplicacaoDTO(Integer id, String descricao_apl, Integer idmodulo, String descricao_mod, String arquivo_inic) {
        this.id            = id;
        this.descricao_apl = descricao_apl;
        this.descricao_mod = descricao_mod;
        this.idmodulo      = idmodulo;
        this.arquivo_inic  = arquivo_inic;
    }
}
