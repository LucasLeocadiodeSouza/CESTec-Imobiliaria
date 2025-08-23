package com.cestec.cestec.model.opr;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class opr_chamados_solic_horarioId implements Serializable {
    
    @Column(name = "seq")
    private Integer seq;

    @Column(name = "id_chamados_solic")
    private Integer id_chamados_solic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof opr_chamados_solic_horarioId)) return false;
        opr_chamados_solic_horarioId that = (opr_chamados_solic_horarioId) o;
        return Objects.equals(seq, that.seq) &&
               Objects.equals(id_chamados_solic, that.id_chamados_solic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, id_chamados_solic);
    }
}
