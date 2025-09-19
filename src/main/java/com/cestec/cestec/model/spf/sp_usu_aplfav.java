package com.cestec.cestec.model.spf;

import java.time.LocalDate;

import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.sp_aplicacoes;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "sp_usu_aplfav")
public class sp_usu_aplfav {
    @EmbeddedId
    private sp_usu_aplfavId id;

    @ManyToOne
    @MapsId("id_funcionario")
    @JoinColumn(name = "id_funcionario")
    private funcionario funcionario;

    @ManyToOne
    @MapsId("id_aplicacao")
    @JoinColumn(name = "id_aplicacao")
    private sp_aplicacoes aplicacoes;

    private LocalDate datregistro;
    private String    ideusu; 
}
