package com.cestec.cestec.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class LibAcessoId implements Serializable {
    @Column(name = "idusu")
    private Integer idUsuario;
    
    @Column(name = "codapl")
    private Integer codAplicacao;

    public LibAcessoId() {}
    
    public LibAcessoId(Integer idUsuario, Integer codAplicacao) {
        this.idUsuario = idUsuario;
        this.codAplicacao = codAplicacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        LibAcessoId that = (LibAcessoId) o;
        
        if (!idUsuario.equals(that.idUsuario)) return false;
        return codAplicacao.equals(that.codAplicacao);
    }

    @Override
    public int hashCode() {
        int result = idUsuario.hashCode();
        result = 31 * result + codAplicacao.hashCode();
        return result;
    }
}
