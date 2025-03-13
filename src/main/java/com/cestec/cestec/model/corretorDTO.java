package com.cestec.cestec.model;

import java.sql.Date;

public class corretorDTO {
    
    private Integer codfuncionario;
    private Integer codcorretor;
    //private Integer codsetor;
    private double  salario;
    private String  nome;
    private Date    datinasc;
    private String  cpf;
    private String  numtel;
    private String  endereco;
    private String  email;
    private String  login;

    public corretorDTO(){}

    public corretorDTO(Integer codcorretor, String email, Integer codfuncionario, String nome, String login) {
        this.codfuncionario = codfuncionario;
        this.codcorretor = codcorretor;
        this.nome = nome;
        this.email = email;
        this.login = login;
    }

    public Integer getCodfuncionario() {
        return codfuncionario;
    }

    public void setCodfuncionario(Integer codfuncionario) {
        this.codfuncionario = codfuncionario;
    }

    public Integer getCodcorretor() {
        return codcorretor;
    }

    public void setCodcorretor(Integer codcorretor) {
        this.codcorretor = codcorretor;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }


}
