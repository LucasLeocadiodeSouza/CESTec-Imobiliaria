package com.cestec.cestec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pcp_corretor")
public class pcp_corretor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codcorretor;

    @OneToOne
    @JoinColumn(name = "codfuncionario", nullable = false)
    private funcionario funcionario;

    public pcp_corretor(Integer codcorretor, String email) {
        this.codcorretor = codcorretor;
        this.email       = email;
    }

    private String email;
}
