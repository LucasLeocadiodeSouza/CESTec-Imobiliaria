package com.cestec.cestec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pcp_setor")
public class pcp_setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codsetor;

    private String  nome;
}
