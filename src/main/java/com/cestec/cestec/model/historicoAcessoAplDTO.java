package com.cestec.cestec.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class historicoAcessoAplDTO {
    private Integer   idhistorico;
    private Integer   idmodulos;
    private Integer   idaplicacao;
    private Integer   idfunc;
    private String    descapl;
    private String    descmodulo;
    private Integer   numacess;
    private Boolean   indcadastro;
    private Boolean   indliberacao;
    private Boolean   indanalise;
    private Boolean   indgestao;
    private LocalDate atualizado_em;
    private LocalDate datregistro;
    private String    ideusu;

    public historicoAcessoAplDTO(Integer idhistorico, Integer idmodulos, Integer idaplicacao, Integer idfunc, String descapl, String descmodulo, Integer numacess, Boolean indcadastro, Boolean indliberacao, Boolean indanalise, Boolean indgestao){
        this.idhistorico  = idhistorico;
        this.idmodulos    = idmodulos;
        this.idaplicacao  = idaplicacao;
        this.idfunc       = idfunc;
        this.descapl      = descapl;
        this.descmodulo   = descmodulo;
        this.numacess     = numacess;
        this.indcadastro  = indcadastro;
        this.indliberacao = indliberacao;
        this.indanalise   = indanalise;
        this.indgestao    = indgestao;
    }
}
