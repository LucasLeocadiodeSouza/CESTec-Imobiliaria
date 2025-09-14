package com.cestec.cestec.model.cri;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "pcp_cliente", cascade = CascadeType.ALL, orphanRemoval = false)    
    @JsonIgnore
    private List<pcp_contrato> pcp_contratos;

    @Column(length = 80)
    private String nome;

    @Column(length = 20)
    private String documento;
    
    @Column(length = 7)
    private String id_usuario;

    private String        numtel;
    private String        email;
    private LocalDate     criado_em;
    private LocalDate     atualizado_em;

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
