package com.cestec.cestec.model.cri;

import java.io.Serializable;
import java.util.Objects;

import com.cestec.cestec.model.opr.opr_chamados_solic_horarioId;

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
public class pcp_imovel_imgId implements Serializable {
    @Column(name = "seq")
    private Integer seq;

    @Column(name = "codimovel")
    private Integer codimovel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof pcp_imovel_imgId)) return false;
        pcp_imovel_imgId that = (pcp_imovel_imgId) o;
        return Objects.equals(seq, that.seq) &&
               Objects.equals(codimovel, that.codimovel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, codimovel);
    }
}
