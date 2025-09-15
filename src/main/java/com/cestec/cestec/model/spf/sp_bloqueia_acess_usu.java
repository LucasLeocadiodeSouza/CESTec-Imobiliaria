package com.cestec.cestec.model.spf;

import java.time.LocalDate;
import com.cestec.cestec.model.funcionario;
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
@Entity(name = "sp_bloqueia_acess_usu")
public class sp_bloqueia_acess_usu {
    @EmbeddedId
    private sp_bloqueia_acess_usuId seq;

    @ManyToOne
    @MapsId("id_funcionario")
    @JoinColumn(name = "id_funcionario")
    private funcionario funcionario;

    @ManyToOne
    @MapsId("id_bloqueia_acess")
    @JoinColumn(name = "id_bloqueia_acess")
    private sp_bloqueia_acess bloqueio_acess;

    private boolean   ativo;
    private LocalDate datregistro;
    private String    ideusu; 
}
