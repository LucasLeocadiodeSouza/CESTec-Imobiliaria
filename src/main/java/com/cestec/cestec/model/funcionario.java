package com.cestec.cestec.model;

import java.sql.Date;

import com.cestec.cestec.model.securityLogin.sp_user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "funcionario")
public class funcionario {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codfuncionario;

    @OneToOne
    private sp_user sp_user;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private cargo cargo;

    @ManyToOne
    @JoinColumn(name = "setor_id", nullable = false)
    private pcp_setor setor;

    //private Integer codsetor;
    private double  salario;
    private String  nome;
    private Date    datinasc;
    private String  cpf;
    private String  numtel;
    private String  endereco;
    
}
