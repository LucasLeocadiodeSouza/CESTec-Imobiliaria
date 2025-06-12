package com.cestec.cestec.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "pcp_cliente")
public class pcp_cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codcliente;

    @OneToOne(mappedBy = "pcp_cliente", cascade = CascadeType.ALL, orphanRemoval = false)    
    @JsonIgnore
    private pcp_contrato pcp_contrato;

    @Column(length = 80)
    private String nome;

    @Column(length = 20)
    private String documento;
    
    @Column(length = 7)
    private String id_usuario;

    private String        numtel;
    private String        email;
    private LocalDateTime criado_em;
    private LocalDateTime atualizado_em;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean pessoa_fisica;

    @Column(length = 80)
    private String endereco_logradouro;

    @Column(length = 10)
    private String endereco_numero;

    @Column(length =  70)
    private String endereco_cidade;

    @Column(length = 70)
    private String endereco_bairro;

    @Column(length = 40)
    private String endereco_complemento;

    @Column(length = 2)
    private String endereco_uf;

    @Column(length = 20)
    private String endereco_cep;

    public boolean isPf() {
        return pessoa_fisica;
    }
    public void setPf(boolean pessoa_fisica) {
        this.pessoa_fisica = pessoa_fisica;
    }
}
