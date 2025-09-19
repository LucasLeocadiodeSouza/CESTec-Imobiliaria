package com.cestec.cestec.model.spf;

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
@Table(name = "sp_usu_preferencia")
public class sp_usu_preferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_funcionario", nullable = false)
    private funcionario func;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean mini_cab;

    @Column(length = 7)
    private String fonte_texto;

    @Column(length = 6)
    private String tema_menu;
}
