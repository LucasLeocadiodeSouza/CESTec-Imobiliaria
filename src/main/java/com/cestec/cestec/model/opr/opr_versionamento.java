package com.cestec.cestec.model.opr;

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
@Entity(name = "opr_versionamento")
public class opr_versionamento {
    @EmbeddedId
    private opr_versionamentoId seq;

    @ManyToOne
    @MapsId("id_chamados_solic")
    @JoinColumn(name = "id_chamados_solic")
    private opr_chamados_solic chamadoSolic;

    private LocalDate datregistro;
    private String    ideusu;  
}
