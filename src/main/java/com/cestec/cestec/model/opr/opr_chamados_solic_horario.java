package com.cestec.cestec.model.opr;

import java.sql.Time;
import java.time.LocalDate;
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
@Entity(name = "opr_chamados_solic_horario")
public class opr_chamados_solic_horario {
    @EmbeddedId
    private opr_chamados_solic_horarioId id;

    @ManyToOne
    @MapsId("id_chamados_solic")
    @JoinColumn(name = "id_chamados_solic")
    private opr_chamados_solic chamadoSolic;

    private LocalDate datahist;
    private Time      horaini;
    private Time      horafim;
    private LocalDate datregistro;
    private String    ideususolic;
}
