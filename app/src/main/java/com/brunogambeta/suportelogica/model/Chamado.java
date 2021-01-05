package com.brunogambeta.suportelogica.model;

/*
    Created by Bruno on 20/10/2020
*/
public class Chamado {

    private String NUMEROCHAMADO;
    private String CNPJ;
    private String RAZAOSOCIAL;
    private String NOMEPARACONTATO;
    private String TELEFONEPARACONTATO;
    private String DESCRICAODOPROBLEMADUVIDA;

    public Chamado(){

    }

    public Chamado (String NUMEROCHAMADO, String CNPJ, String RAZAOSOCIAL, String NOMEPARACONTATO, String TELEFONEPARACONTATO, String DESCRICAODOPROBLEMADUVIDA) {
        this.NUMEROCHAMADO = NUMEROCHAMADO;
        this.CNPJ = CNPJ;
        this.RAZAOSOCIAL = RAZAOSOCIAL;
        this.NOMEPARACONTATO = NOMEPARACONTATO;
        this.TELEFONEPARACONTATO = TELEFONEPARACONTATO;
        this.DESCRICAODOPROBLEMADUVIDA = DESCRICAODOPROBLEMADUVIDA;
    }

    public String getNumeroChamado () {
        return NUMEROCHAMADO;
    }

    public void setNumeroChamado (String NUMEROCHAMADO) {
        this.NUMEROCHAMADO = NUMEROCHAMADO;
    }

    public String getCnpj () {
        return CNPJ;
    }

    public void setCnpj (String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRazaoSocial () {
        return RAZAOSOCIAL;
    }

    public void setRazaoSocial (String RAZAOSOCIAL) {
        this.RAZAOSOCIAL = RAZAOSOCIAL;
    }

    public String getNomeParaContato () {
        return NOMEPARACONTATO;
    }

    public void setNomeParaContato (String NOMEPARACONTATO) {
        this.NOMEPARACONTATO = NOMEPARACONTATO;
    }

    public String getTelefoneParaContato () {
        return TELEFONEPARACONTATO;
    }

    public void setTelefoneParaContato (String TELEFONEPARACONTATO) {
        this.TELEFONEPARACONTATO = TELEFONEPARACONTATO;
    }

    public String getDescricaoProblemaDuvida () {
        return DESCRICAODOPROBLEMADUVIDA;
    }

    public void setDescricaoProblemaDuvida (String DESCRICAODOPROBLEMADUVIDA) {
        this.DESCRICAODOPROBLEMADUVIDA = DESCRICAODOPROBLEMADUVIDA;
    }
}
