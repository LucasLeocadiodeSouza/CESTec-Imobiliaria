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
    
    @Column(name = "role")
    private Integer role;

    public LibAcessoId() {}
    
    public LibAcessoId(Integer idUsuario, Integer role) {
        this.idUsuario = idUsuario;
        this.role      = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        LibAcessoId that = (LibAcessoId) obj;
        
        if (!idUsuario.equals(that.idUsuario)) return false;
        return role.equals(that.role);
    }

    @Override
    public int hashCode() {
        int result = idUsuario.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
