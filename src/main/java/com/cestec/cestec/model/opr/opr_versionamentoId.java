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
public class opr_versionamentoId implements Serializable{
    
    @Column(name = "codmerge")
    private Integer codmerge;

    @Column(name = "branch_name")
    private String  branch_name;

    @Column(name = "prog")
    private String  prog;

    @Column(name = "id_chamados_solic")
    private Integer id_chamados_solic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof opr_versionamentoId)) return false;
        opr_versionamentoId that = (opr_versionamentoId) o;
        return Objects.equals(codmerge, that.codmerge) &&
               Objects.equals(branch_name, that.branch_name) &&
               Objects.equals(prog, that.prog) &&
               Objects.equals(id_chamados_solic, that.id_chamados_solic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codmerge,branch_name,prog,id_chamados_solic);
    }
}
