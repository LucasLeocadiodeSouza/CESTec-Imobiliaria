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
public class sp_bloqueia_acess_usuId implements Serializable{
    @Column(name = "id_funcionario")
    private Integer id_funcionario;

    @Column(name = "id_bloqueia_acess")
    private Integer id_bloqueia_acess;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof sp_bloqueia_acess_usuId)) return false;
        sp_bloqueia_acess_usuId that = (sp_bloqueia_acess_usuId) o;
        return Objects.equals(id_funcionario, that.id_funcionario) &&
               Objects.equals(id_bloqueia_acess, that.id_bloqueia_acess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_funcionario,id_bloqueia_acess);
    }
}
