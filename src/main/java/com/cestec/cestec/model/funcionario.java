package com.cestec.cestec.model;

import java.sql.Date;

import com.cestec.cestec.model.securityLogin.sp_user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionario")
public class funcionario {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codfuncionario;

    @OneToOne
    private sp_user sp_user;

    //private Integer codsetor;
    private double  salario;
    private String  nome;
    private Date    datinasc;
    private String  cpf;
    private String  numtel;
    private String  endereco;


    public Integer getCodfuncionario() {
        return codfuncionario;
    }
    public void setCodfuncionario(Integer codfuncionario) {
        this.codfuncionario = codfuncionario;
    }
    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Date getDatinasc() {
        return datinasc;
    }
    public void setDatinasc(Date datinasc) {
        this.datinasc = datinasc;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNumtel() {
        return numtel;
    }
    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public sp_user getSp_user() {
        return sp_user;
    }
    public void setSp_user(sp_user sp_user) {
        this.sp_user = sp_user;
    }


    
}
