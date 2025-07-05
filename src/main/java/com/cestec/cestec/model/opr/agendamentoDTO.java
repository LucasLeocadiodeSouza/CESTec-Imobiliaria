package com.cestec.cestec.model.opr;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class agendamentoDTO {
    private Integer    codagendfunc;
    private String     titulo;
    private String     descricao;
    private Integer    motivo;
    private Integer    codfunc;
    private Integer    codagenda;
    private LocalDate  datagen;
    private String     horagen;
    private LocalTime  horagen2;
    private String     ideusu;
    private LocalDate  datiregistro;
    private String     corAgend;

    public agendamentoDTO(Integer codagenda, String titulo, String descricao, Integer motivo, LocalDate datagen, LocalTime horagen2, String ideusu) {
        this.titulo    = titulo;
        this.descricao = descricao;
        this.motivo    = motivo;
        this.codagenda = codagenda;
        this.datagen   = datagen;
        this.horagen2  = horagen2;
        this.ideusu    = ideusu;
    }

    public agendamentoDTO(Integer codagendfunc, String titulo, String descricao, Integer motivo, Integer codfunc, Integer codagenda, LocalDate datagen, String horagen, LocalTime horagen2, String ideusu, LocalDate datiregistro) {
        this.codagendfunc = codagendfunc;
        this.titulo       = titulo;
        this.descricao    = descricao;
        this.motivo       = motivo;
        this.codfunc      = codfunc;
        this.codagenda    = codagenda;
        this.datagen      = datagen;
        this.horagen      = horagen;
        this.horagen2     = horagen2;
        this.ideusu       = ideusu;
        this.datiregistro = datiregistro;
    }

    public agendamentoDTO(Integer codagendfunc, Integer motivo, Integer codagenda, String corAgend, LocalDate datagen, LocalTime horagen2) {
        this.codagendfunc = codagendfunc;
        this.motivo       = motivo;
        this.codagenda    = codagenda;
        this.corAgend     = corAgend;
        this.datagen      = datagen;
        this.horagen2     = horagen2;
    }
}
