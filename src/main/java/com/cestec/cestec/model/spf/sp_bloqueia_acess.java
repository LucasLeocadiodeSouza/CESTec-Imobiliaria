package com.cestec.cestec.model.spf;

import java.time.LocalDate;

import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.pcp_setor;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.model.sp_modulos;
import com.cestec.cestec.model.opr.opr_chamados_solic;
import com.cestec.cestec.model.opr.opr_versionamentoId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "sp_bloqueia_acess")
public class sp_bloqueia_acess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_modulo", nullable = false)
    private sp_modulos modulo;

    @ManyToOne
    @JoinColumn(name = "id_aplicacao", nullable = false)
    private sp_aplicacoes aplicacao;

    private boolean   ativo;
    private LocalDate datregistro;
    private String    ideusu; 
}
