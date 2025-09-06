package com.cestec.cestec.model.cri;

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
public class pcp_contratoId implements Serializable {
    @Column(name = "codcontrato")
    private Integer codcontrato;

    @Column(name = "codimovel")
    private Integer codimovel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof pcp_contratoId)) return false;
        pcp_contratoId that = (pcp_contratoId) o;
        return Objects.equals(codcontrato, that.codcontrato) &&
               Objects.equals(codimovel, that.codimovel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codcontrato, codimovel);
    }
}
