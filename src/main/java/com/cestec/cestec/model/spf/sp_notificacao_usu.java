package com.cestec.cestec.model.spf;

import java.time.LocalDate;

import com.cestec.cestec.model.funcionario;

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
@Table(name = "sp_notificacao_usu")
public class sp_notificacao_usu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="idfunc", nullable = false)
    private funcionario func;

    @Column(length = 60)
    private String descricao;

    private boolean   ativo;
    private LocalDate datregistro;
    private String    ideusu;
}