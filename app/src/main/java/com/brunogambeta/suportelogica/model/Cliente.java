package com.brunogambeta.suportelogica.model;

/*
    Created by Bruno on 20/10/2020
*/
public class Cliente {


    private String cnpj;
    private String razaoSocial;
    private String numeroChamado;

    public Cliente (String cnpj, String razaoSocial, String numeroChamado) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.numeroChamado = numeroChamado;
    }

    public Cliente () {
    }

    public String getNumeroChamado () {
        return numeroChamado;
    }

    public void setNumeroChamado (String numeroChamado) {
        this.numeroChamado = numeroChamado;
    }

    public String getCnpj () {
        return cnpj;
    }

    public void setCnpj (String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial () {
        return razaoSocial;
    }

    public void setRazaoSocial (String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

}
