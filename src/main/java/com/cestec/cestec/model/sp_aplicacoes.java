package com.cestec.cestec.model;

import java.sql.Date;
import java.time.LocalDate;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sp_aplicacoes")
public class sp_aplicacoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="idmodulos", nullable = false)
    private sp_modulos modulo;

    @ManyToOne
    @JoinColumn(name="role", nullable = false)
    private sp_roleacess role;
    
    private String  descricao;

    @Column(length = 45)
    private String  arquivo_inic;

    private LocalDate datregistro;
    private String    ideusu;
}
