package com.cestec.cestec.model.spf;

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
public class sp_usu_aplfavId implements Serializable{
    @Column(name = "id_funcionario")
    private Integer id_funcionario;

    @Column(name = "id_aplicacao")
    private Integer id_aplicacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof sp_usu_aplfavId)) return false;
        sp_usu_aplfavId that = (sp_usu_aplfavId) o;
        return Objects.equals(id_funcionario, that.id_funcionario) &&
               Objects.equals(id_aplicacao, that.id_aplicacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_funcionario,id_aplicacao);
    }
}
