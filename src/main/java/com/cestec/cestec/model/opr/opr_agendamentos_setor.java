package com.cestec.cestec.model.opr;

import java.time.LocalDate;

import com.cestec.cestec.model.pcp_setor;

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
@Table
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "opr_agendamentos_setor")
public class opr_agendamentos_setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "codsetor", nullable = false)
    private pcp_setor codsetor;

    @ManyToOne
    @JoinColumn(name = "codagenda", nullable = false)
    private opr_agendamentos codagenda;

    private String    ideusu;
    private LocalDate datregistro;
}
