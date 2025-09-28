package com.cestec.cestec.model;

import java.time.LocalDate;
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
    
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean indcadastro;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean indliberacao;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean indanalise;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean indgestao;

    @Column(length = 45)
    private String  arquivo_inic;
    
    private String    descricao;
    private LocalDate datregistro;
    private String    ideusu;
}
