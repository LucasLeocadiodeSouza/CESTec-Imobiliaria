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

    public agendamentoDTO(Integer codagenda, String titulo, String descricao, Integer motivo, LocalDate datagen, LocalTime horagen2, String ideusu) {
        this.titulo    = titulo;
        this.descricao = descricao;
        this.motivo    = motivo;
        this.codagenda = codagenda;
        this.datagen   = datagen;
        this.horagen2  = horagen2;
        this.ideusu    = ideusu;
    }

    
}
